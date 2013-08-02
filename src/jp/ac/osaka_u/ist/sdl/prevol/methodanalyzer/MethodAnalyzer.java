package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.TargetRevisionDetector;
import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;

/**
 * リポジトリを解析してメソッド情報をDBに保存する機能のメインクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodAnalyzer {

	/**
	 * メインメソッド
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// 引数を解析
			final MethodAnalyzerSettings settings = MethodAnalyzerSettings
					.parseArgs(args);

			// リポジトリ，データベースを初期化
			initialize(settings);

			// メインの処理を実行
			mainProcess(settings);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// 後処理
			postprocess();

		}
	}

	/**
	 * 初期化を行う
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void initialize(final MethodAnalyzerSettings settings)
			throws Exception {
		// リポジトリを設定
		SVNRepositoryManager.setup(settings.getRepositoryPath(),
				settings.getAdditionalPath(), settings.getUserName(),
				settings.getPasswd());

		// データベースとのコネクションを生成
		DBConnection.createInstance(settings.getDbPath());
	}

	/**
	 * メイン処理
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void mainProcess(final MethodAnalyzerSettings settings)
			throws Exception {
		// DBとのコネクション
		final DBConnection connection = DBConnection.getInstance();

		// 分析対象リビジョンを特定
		final List<RevisionData> targetRevisions = TargetRevisionDetector
				.detectTargetRevisions(settings.getLanguage(),
						settings.getStartRevision(), settings.getEndRevision());

		// 分析対象リビジョンをDBに登録
		connection.getRevisionRegisterer().register(targetRevisions);

		// 各リビジョンの分析
		analyzeRevisions(targetRevisions, settings.getLanguage(),
				settings.getThreads());
	}

	/**
	 * リビジョン群を解析
	 * 
	 * @param targetRevisions
	 * @param language
	 * @param threadsCount
	 * @throws Exception
	 */
	private static void analyzeRevisions(
			final List<RevisionData> targetRevisions, final Language language,
			final int threadsCount) throws Exception {
		RevisionData previousRevision = null;
		final RevisionData latestRevision = targetRevisions.get(targetRevisions
				.size() - 1);

		for (final RevisionData revision : targetRevisions) {
			analyzeRevision(revision, previousRevision, latestRevision,
					language, threadsCount);
			previousRevision = revision;
		}
	}

	/**
	 * 指定されたリビジョンを解析
	 * 
	 * @param revision
	 * @param previousRevision
	 * @param latestRevision
	 * @param language
	 * @param threadsCount
	 * @throws Exception
	 */
	private static void analyzeRevision(final RevisionData revision,
			final RevisionData previousRevision,
			final RevisionData latestRevision, final Language language,
			final int threadsCount) throws Exception {
		final RevisionAnalyzer analyzer = new RevisionAnalyzer(revision,
				previousRevision, latestRevision, language, threadsCount);
		analyzer.process();
	}

	/**
	 * 後処理を行う
	 */
	private static void postprocess() {
		if (DBConnection.getInstance() != null) {
			DBConnection.getInstance().close();
		}
	}

}
