package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.List;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.TargetRevisionDetector;
import jp.ac.osaka_u.ist.sdl.prevol.setting.Language;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

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
		final long startTime = System.nanoTime();
		
		try {
			// 引数を解析
			final MethodAnalyzerSettings settings = MethodAnalyzerSettings
					.parseArgs(args);

			// 初期化
			initialize(settings);

			// メインの処理を実行
			mainProcess(settings);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// 後処理
			postprocess();

		}
		
		final long endTime = System.nanoTime();
		final long timeElapsed = (endTime - startTime) / 1000000000;
		
		MessagePrinter.stronglyPrintln("operations have finished!!");
		MessagePrinter.stronglyPrintln("\ttotal elapsed time is " + timeElapsed + " [s]");
	}

	/**
	 * 初期化を行う
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void initialize(final MethodAnalyzerSettings settings)
			throws Exception {
		// 出力レベルを設定
		MessagePrinter.setMode(settings.getPrintMode());

		MessagePrinter.stronglyPrintln("operations start");
		MessagePrinter.stronglyPrintln();

		// リポジトリを設定
		MessagePrinter.stronglyPrintln("initializing repository ... ");
		SVNRepositoryManager.setup(settings.getRepositoryPath(),
				settings.getAdditionalPath(), settings.getUserName(),
				settings.getPasswd());
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();

		// データベースとのコネクションを生成
		MessagePrinter.stronglyPrintln("initializing db ... ");
		DBConnection.createInstance(settings.getDbPath());
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();
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
		MessagePrinter.stronglyPrintln("detecting target revisions ... ");
		final List<RevisionData> targetRevisions = TargetRevisionDetector
				.detectTargetRevisions(settings.getLanguage(),
						settings.getStartRevision(), settings.getEndRevision());
		MessagePrinter.stronglyPrintln("\t" + targetRevisions.size()
				+ " revisions are detected");
		MessagePrinter.stronglyPrintln();

		// 分析対象リビジョンをDBに登録
		connection.getRevisionRegisterer().register(targetRevisions);

		// 各リビジョンの分析
		MessagePrinter.stronglyPrintln("analyzing every revisions ... ");
		analyzeRevisions(targetRevisions, settings.getLanguage(),
				settings.getThreads());
		MessagePrinter.stronglyPrintln();
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
		int analyzedRevisions = 0;

		for (final RevisionData revision : targetRevisions) {
			MessagePrinter.stronglyPrintln("\tnow analyzing revision "
					+ revision.getRevisionNum() + "\t["
					+ (++analyzedRevisions) + "/" + targetRevisions.size()
					+ "]");

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
