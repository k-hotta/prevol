package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
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

	public FilesAnalyzer(final String[] targets, final RevisionData revision,
			final RevisionData latestRevision, final int threadsCount) {
		this.targets = targets;
		this.revision = revision;
		this.latestRevision = latestRevision;
		this.threadsCount = threadsCount;
	}

	public FilesAnalyzer(final Collection<String> targets,
			final RevisionData revision, final RevisionData latestRevision,
			final int threadsCount) {
		this(targets.toArray(new String[0]), revision, latestRevision,
				threadsCount);
	}

	/**
	 * ファイル群をマルチスレッドで解析
	 * 
	 * @return
	 * @throws RepositoryNotInitializedException
	 * @throws SVNException
	 */
	public List<MethodData> analyzeFiles()
			throws RepositoryNotInitializedException, SVNException {
		final AtomicInteger index = new AtomicInteger(0);
		final ConcurrentMap<String, Collection<MethodData>> detectedMethods = new ConcurrentHashMap<String, Collection<MethodData>>();

		final Thread[] threads = new Thread[threadsCount];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new FileAnalyzeThread(targets, revision,
					latestRevision, index, detectedMethods));
			threads[i].start();
		}

		for (final Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		final List<MethodData> result = new ArrayList<MethodData>();
		for (final Map.Entry<String, Collection<MethodData>> entry : detectedMethods
				.entrySet()) {
			result.addAll(entry.getValue());
		}

		return Collections.unmodifiableList(result);
	}

}
