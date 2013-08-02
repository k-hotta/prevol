package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.RepositoryNotInitializedException;

import org.tmatesoft.svn.core.SVNException;

/**
 * 1つのコミットで変更されたファイルをすべて解析するクラス
 * 
 * @author k-hotta
 * 
 */
public class FilesAnalyzer {

	/**
	 * 分析対象となるファイルのパスを格納する配列
	 */
	private final String[] targets;

	/**
	 * 分析対象リビジョン(変更後のリビジョン)
	 */
	private final RevisionData revision;

	/**
	 * 最終リビジョン
	 */
	private final RevisionData latestRevision;

	/**
	 * スレッド数
	 */
	private final int threadsCount;

	/**
	 * 解析したファイルを保持するためのマップ <br>
	 * キーはID，値はファイル
	 */
	private final ConcurrentMap<Long, FileData> analyzedFiles;

	/**
	 * 検出されたメソッドを保持するためのマップ <br>
	 * キーはID，値はメソッド
	 */
	private final ConcurrentMap<Long, MethodData> detectedMethods;

	/**
	 * 検出されたベクトルデータを保持するためのマップ <br>
	 * キーはID，値はベクトルデータ
	 */
	private final ConcurrentMap<Long, VectorData> detectedVectors;

	public FilesAnalyzer(final String[] targets, final RevisionData revision,
			final RevisionData latestRevision, final int threadsCount) {
		this.targets = targets;
		this.revision = revision;
		this.latestRevision = latestRevision;
		this.threadsCount = threadsCount;
		this.analyzedFiles = new ConcurrentHashMap<Long, FileData>();
		this.detectedMethods = new ConcurrentHashMap<Long, MethodData>();
		this.detectedVectors = new ConcurrentHashMap<Long, VectorData>();

	}

	public FilesAnalyzer(final Collection<String> targets,
			final RevisionData revision, final RevisionData latestRevision,
			final int threadsCount) {
		this(targets.toArray(new String[0]), revision, latestRevision,
				threadsCount);
	}

	/**
	 * 解析したファイルを取得
	 * 
	 * @return
	 */
	public final Map<Long, FileData> getAnalyzedFiles() {
		return Collections.unmodifiableMap(analyzedFiles);
	}

	/**
	 * 検出されたメソッドを取得
	 * 
	 * @return
	 */
	public final Map<Long, MethodData> getDetectedMethods() {
		return Collections.unmodifiableMap(detectedMethods);
	}

	/**
	 * 検出されたベクトルデータを取得
	 * 
	 * @return
	 */
	public final Map<Long, VectorData> getDetectedVectors() {
		return Collections.unmodifiableMap(detectedVectors);
	}

	/**
	 * ファイル群をマルチスレッドで解析 <br>
	 * これの実行が終わったら各フィールドに解析結果が保存される
	 * 
	 * @throws RepositoryNotInitializedException
	 * @throws SVNException
	 */
	public void analyzeFiles() throws RepositoryNotInitializedException,
			SVNException {
		final AtomicInteger index = new AtomicInteger(0);

		final Thread[] threads = new Thread[threadsCount];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new FileAnalyzeThread(targets, revision,
					latestRevision, index, analyzedFiles, detectedMethods,
					detectedVectors));
			threads[i].start();
		}

		for (final Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
