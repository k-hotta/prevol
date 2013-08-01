package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.RepositoryNotInitializedException;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;

import org.eclipse.jdt.core.dom.CompilationUnit;
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

	public FilesAnalyzer(final String[] targets, final RevisionData revision,
			final RevisionData latestRevision) {
		this.targets = targets;
		this.revision = revision;
		this.latestRevision = latestRevision;
	}

	public FilesAnalyzer(final Collection<String> targets,
			final RevisionData revision, final RevisionData latestRevision) {
		this(targets.toArray(new String[0]), revision, latestRevision);
	}

	public List<MethodData> analyzeFiles()
			throws RepositoryNotInitializedException, SVNException {
		// 特定されたメソッドを保持するためのリスト
		final List<MethodData> detectedMethods = new ArrayList<MethodData>();

		// 各ファイルについて
		for (final String target : targets) {
			// ASTを構築
			final String source = SVNRepositoryManager.getFileContents(
					revision.getRevisionNum(), target);
			final CompilationUnit rootNode = ASTCreator.createAST(source);

			// ASTをパース
			final ASTAnalyzer analyzer = new ASTAnalyzer(revision,
					latestRevision, target);
			rootNode.accept(analyzer);

			// 特定されたメソッドを保存
			detectedMethods.addAll(analyzer.getMethods());
		}

		return Collections.unmodifiableList(detectedMethods);
	}

}
