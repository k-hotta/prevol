package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.FileData;
import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.ChangedFilesDetector;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.RepositoryNotInitializedException;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

import org.tmatesoft.svn.core.SVNException;

/**
 * リビジョンを分析してメソッドを特定するクラス
 * 
 * @author k-hotta
 * 
 */
public class RevisionAnalyzer {

	/**
	 * DBとのコネクション
	 */
	private final DBConnection connection;

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
			final RevisionData latestRevision, final Language language,
			final int threadsCount) {
		this.connection = DBConnection.getInstance();
		this.revision = revision;
		this.previousRevision = previousRevision;
		this.latestRevision = latestRevision;
		this.isFirstRevision = (previousRevision == null);
		this.language = language;
		this.threadsCount = threadsCount;
		this.changedFilesDetector = new ChangedFilesDetector(language);
	}

	public void process() throws Exception {
		// 最初のリビジョンのとき
		if (isFirstRevision) {
			// すべてのソースファイルが分析の対象
			final List<String> sourceFiles = SVNRepositoryManager
					.getListOfSourceFiles(revision.getRevisionNum(), language,
							null);
			analyzeAndRegister(sourceFiles);
		}

		// 2番目以降のリビジョンのとき
		else {
			// 追加，削除，変更されたファイルを特定
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

			// 削除されたファイル or 変更されたファイルに含まれる要素の endRevisionId を更新
			updateElementsInDeletedFiles(deletedOrChanged);

			// 追加されたファイル or 変更されたファイルを分析し，データを登録
			analyzeAndRegister(addedOrChanged);
		}
	}

	/**
	 * 引数で指定されたパスのソースファイルをすべて分析し，得られたデータをDBに登録
	 * 
	 * @param analyzer
	 */
	private void analyzeAndRegister(final Collection<String> sourceFiles)
			throws Exception {
		// ソースファイルを分析
		final FilesAnalyzer analyzer = new FilesAnalyzer(sourceFiles, revision,
				latestRevision, threadsCount);
		analyzer.analyzeFiles();

		// 分析結果を登録
		connection.getFileRegisterer().register(
				analyzer.getAnalyzedFiles().values());
		connection.getMethodRegisterer().register(
				analyzer.getDetectedMethods().values());
		connection.getVectorRegisterer().register(
				analyzer.getDetectedVectors().values());
	}

	/**
	 * 削除されたファイル，および変更されたファイルに含まれる要素の終了リビジョンを更新する
	 * 
	 * @param deletedOrChangedFilePaths
	 */
	private void updateElementsInDeletedFiles(
			final List<String> deletedOrChangedFilePaths) throws Exception {
		// 削除，変更されたファイルがない場合は何もしない
		if (deletedOrChangedFilePaths.isEmpty()) {
			return;
		}

		// 削除，変更されたファイルのIDを取得
		final List<Long> deletedOrChangedFileIds = detectDeletedOrChangedFileIds(deletedOrChangedFilePaths);

		// ファイルテーブルを更新
		connection.getFileRegisterer().updatePreviousRevisionFiles(
				deletedOrChangedFileIds, previousRevision.getId());

		// 削除，変更されたファイルに含まれるメソッドのIDを取得
		final List<Long> methodIdsToBeUpdated = detectMethodIdsToBeUpdated(deletedOrChangedFileIds);

		// メソッドテーブルを更新
		connection.getMethodRegisterer().updatePreviousRevisionMethods(
				methodIdsToBeUpdated, previousRevision.getId());
	}

	/**
	 * 引数で指定されたパスを持つファイルのIDを取得する
	 * 
	 * @param deletedOrChangedFilePaths
	 * @return
	 * @throws SVNException
	 * @throws RepositoryNotInitializedException
	 * @throws SQLException
	 */
	private List<Long> detectDeletedOrChangedFileIds(
			final List<String> deletedOrChangedFilePaths) throws SVNException,
			RepositoryNotInitializedException, SQLException {
		final List<String> deletedOrChangedFiles = SVNRepositoryManager
				.getListOfSourceFiles(previousRevision.getRevisionNum(),
						language, deletedOrChangedFilePaths);
		final SortedSet<FileData> previousFiles = connection.getFileRetriever()
				.retrieveInSpecifiedRevision(previousRevision.getId());
		final List<Long> deletedOrChangedFileIds = new ArrayList<Long>();

		for (final FileData file : previousFiles) {
			for (final String deletedOrChangedFile : deletedOrChangedFiles) {
				if (file.getPath().equals(deletedOrChangedFile)) {
					deletedOrChangedFileIds.add(file.getId());
					break;
				}
			}
			if (deletedOrChangedFileIds.size() == deletedOrChangedFiles.size()) {
				break;
			}
		}
		return deletedOrChangedFileIds;
	}

	/**
	 * 引数で指定されたファイルに含まれるメソッドのIDを取得する
	 * 
	 * @param deletedOrChangedFileIds
	 * @return
	 * @throws SQLException
	 */
	private List<Long> detectMethodIdsToBeUpdated(
			final List<Long> deletedOrChangedFileIds) throws SQLException {
		final Set<MethodData> methodsToBeUpdated = connection
				.getMethodRetriever().retrieveInSpecifiedFiles(
						deletedOrChangedFileIds);
		final List<Long> methodIdsToBeUpdated = new ArrayList<Long>();
		for (final MethodData methodToBeUpdated : methodsToBeUpdated) {
			methodIdsToBeUpdated.add(methodToBeUpdated.getId());
		}
		return methodIdsToBeUpdated;
	}

}
