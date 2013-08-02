package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.ChangedFilesDetector;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

/**
 * リビジョンを分析してメソッドを特定するクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionAnalyzer {

	/**
	 * 解析対象リビジョン (変更後)
	 */
	private final RevisionData revision;

	/**
	 * 変更前リビジョン
	 */
	private final RevisionData previousRevision;

	/**
	 * 最終リビジョン
	 */
	private final RevisionData latestRevision;

	/**
	 * 先頭リビジョンかどうか
	 */
	private final boolean isFirstRevision;

	/**
	 * 対象言語
	 */
	private final Language language;

	/**
	 * スレッド数
	 */
	private final int threadsCount;

	/**
	 * 変更されたファイルを特定するインスタンス
	 */
	private final ChangedFilesDetector changedFilesDetector;

	public RevisionAnalyzer(final RevisionData revision,
			final RevisionData previousRevision,
			final RevisionData latestRevision,
			final Language language, final int threadsCount) {
		this.revision = revision;
		this.previousRevision = previousRevision;
		this.latestRevision = latestRevision;
		this.isFirstRevision = (previousRevision == null);
		this.language = language;
		this.threadsCount = threadsCount;
		this.changedFilesDetector = new ChangedFilesDetector(language);
	}

	public void process() throws Exception {
		if (isFirstRevision) {
			final List<String> sourceFiles = SVNRepositoryManager
					.getListOfSourceFiles(revision.getRevisionNum(), language,
							null);
			final FilesAnalyzer analyzer = new FilesAnalyzer(sourceFiles,
					revision, latestRevision, threadsCount);
			
		} else {
			final Map<String, Character> changedFilesOrDirs = changedFilesDetector
					.detectChangedFiles(revision.getRevisionNum());

			final List<String> addedOrChanged = new ArrayList<String>();
			final List<String> deletedOrChanged = new ArrayList<String>();

			for (final Map.Entry<String, Character> changed : changedFilesOrDirs
					.entrySet()) {
				if (changed.getValue() == 'A') {
					addedOrChanged.add(changed.getKey());
				} else if (changed.getValue() == 'M') {
					addedOrChanged.add(changed.getKey());
					deletedOrChanged.add(changed.getKey());
				} else {
					deletedOrChanged.add(changed.getKey());
				}
			}
		}
	}
}
