package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
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
	 * 検出されたメソッドを保持するためのマップ <br>
	 * キーはファイル，値はそのファイルに含まれるメソッドのコレクション
	 */
	private final ConcurrentMap<FileData, Collection<MethodData>> detectedMethods;

	public FileAnalyzeThread(final String[] targets,
			final RevisionData revision, final RevisionData latestRevision,
			final AtomicInteger index,
			ConcurrentMap<FileData, Collection<MethodData>> detectedMethods) {
		this.targets = targets;
		this.revision = revision;
		this.latestRevision = latestRevision;
		this.index = index;
		this.detectedMethods = detectedMethods;
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
				detectedMethods.put(file, analyzer.getMethods());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
