package jp.ac.osaka_u.ist.sdl.prevol.vectorlinker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.svn.SVNRepositoryManager;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * 変化前後のベクトルの対を特定する機能のメインクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorLinker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final long startTime = System.nanoTime();

		try {
			// 引数を解析
			final VectorLinkerSettings settings = VectorLinkerSettings
					.parseArgs(args);

			// 初期化
			initialize(settings);

			// メイン処理
			mainProcess(settings);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postprocess();
		}

		final long endTime = System.nanoTime();
		final long timeElapsed = (endTime - startTime) / 1000000000;

		MessagePrinter.stronglyPrintln("operations have finished!!");
		MessagePrinter.stronglyPrintln("\ttotal elapsed time is " + timeElapsed
				+ " [s]");
	}

	/**
	 * 初期化を行う
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void initialize(final VectorLinkerSettings settings)
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

	private static void mainProcess(final VectorLinkerSettings settings)
			throws Exception {
		// DBとのコネクション
		final DBConnection connection = DBConnection.getInstance();

		// 分析対象リビジョンをDBから復元
		MessagePrinter.stronglyPrintln("retrieving target revisions ... ");
		final SortedSet<RevisionData> revisions = connection
				.getRevisionRetriever().retrieveAll();

		final long startRevisionNum = settings.getStartRevision();
		final long endRevisionNum = settings.getEndRevision();
		final List<RevisionData> targetRevisions = new ArrayList<RevisionData>();
		for (final RevisionData revision : revisions) {
			if (revision.getRevisionNum() < startRevisionNum) {
				continue;
			}
			if (revision.getRevisionNum() > endRevisionNum) {
				break;
			}
			targetRevisions.add(revision);
		}

		final Map<RevisionData, RevisionData> revisionPairs = RevisionPairDetector
				.detectRevisionPairs(targetRevisions);

		MessagePrinter.stronglyPrintln("\t" + targetRevisions.size()
				+ " revisions are retrieved (" + revisionPairs.size()
				+ " pairs of two consecutive revisions)");

		MessagePrinter.stronglyPrintln();
		runMainThreads(settings, targetRevisions, revisionPairs);
		MessagePrinter.stronglyPrintln();
	}

	/**
	 * 各リビジョンペアをマルチスレッドで処理
	 */
	private static void runMainThreads(final VectorLinkerSettings settings,
			final List<RevisionData> revisions,
			final Map<RevisionData, RevisionData> revisionPairs) {
		final int threadsCount = settings.getThreads();
		final double threshold = settings.getSimilarityThreshold();

		final AtomicInteger index = new AtomicInteger(0);
		final RevisionData[] revisionsArray = revisions
				.toArray(new RevisionData[0]);
		final ConcurrentMap<RevisionData, RevisionData> concurrentRevisionPairs = new ConcurrentHashMap<RevisionData, RevisionData>();
		concurrentRevisionPairs.putAll(revisionPairs);

		final Thread[] threads = new Thread[threadsCount];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(new VectorPairDetectThread(index,
					revisionsArray, concurrentRevisionPairs, threshold));
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

	/**
	 * 後処理を行う
	 */
	private static void postprocess() {
		if (DBConnection.getInstance() != null) {
			DBConnection.getInstance().close();
		}
	}
}
