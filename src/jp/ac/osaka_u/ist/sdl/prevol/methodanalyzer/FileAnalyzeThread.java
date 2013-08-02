package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * ファイル解析用スレッドを表すクラス
 * 
 * @author k-hotta
 * 
 */
public class FileAnalyzeThread implements Runnable {

	/**
	 * 解析対象ファイル
	 */
	private final String[] targets;

	/**
	 * 解析対象リビジョン
	 */
	private final RevisionData revision;

	/**
	 * 最終リビジョン
	 */
	private final RevisionData latestRevision;

	/**
	 * インデックス
	 */
	private final AtomicInteger index;

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

	public FileAnalyzeThread(final String[] targets,
			final RevisionData revision, final RevisionData latestRevision,
			final AtomicInteger index,
			final ConcurrentMap<Long, FileData> analyzedFiles,
			final ConcurrentMap<Long, MethodData> detectedMethods,
			final ConcurrentMap<Long, VectorData> detectedVectors) {
		this.targets = targets;
		this.revision = revision;
		this.latestRevision = latestRevision;
		this.index = index;
		this.analyzedFiles = analyzedFiles;
		this.detectedMethods = detectedMethods;
		this.detectedVectors = detectedVectors;
	}

	@Override
	public void run() {
		while (true) {
			final int currentIndex = index.getAndIncrement();

			if (currentIndex >= targets.length) {
				break;
			}

			try {
				// 分析対象ファイル
				final String target = targets[currentIndex];

				// ファイル情報を構築
				final FileData file = new FileData(revision.getId(),
						latestRevision.getId(), target);

				// ASTを構築
				final String source = SVNRepositoryManager.getFileContents(
						revision.getRevisionNum(), target);
				final CompilationUnit rootNode = ASTCreator.createAST(source);

				// ASTをパース
				final ASTAnalyzer analyzer = new ASTAnalyzer(revision,
						latestRevision, file.getId());
				rootNode.accept(analyzer);

				// 結果を保存
				analyzedFiles.put(file.getId(), file);
				detectedMethods.putAll(analyzer.getMethods());
				detectedVectors.putAll(analyzer.getVectors());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
