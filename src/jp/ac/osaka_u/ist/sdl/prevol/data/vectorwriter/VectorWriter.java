package jp.ac.osaka_u.ist.sdl.prevol.data.vectorwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.ac.osaka_u.ist.sdl.prevol.data.MethodData;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.db.DBConnection;
import jp.ac.osaka_u.ist.sdl.prevol.db.retriever.MethodDataRetriever;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * ベクトル対をCSVファイルに書き出すクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorWriter {

	/**
	 * 出力器
	 */
	private static PrintWriter pw;

	/**
	 * メインメソッド
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final VectorWriterSettings settings = VectorWriterSettings
					.parseArgs(args);

			initialize(settings);

			if (settings.getMode() == VectorWriterMode.TRAINING) {
				writeTrainingSet(settings);
			} else if (settings.getMode() == VectorWriterMode.EVALUATION) {
				writeEvaluationSet(settings);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postprocess();
		}
	}

	/**
	 * 初期設定を行う
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void initialize(final VectorWriterSettings settings)
			throws Exception {
		// 出力レベルを設定
		MessagePrinter.setMode(settings.getPrintMode());

		MessagePrinter.stronglyPrintln("operations start");
		MessagePrinter.stronglyPrintln();

		// データベースとのコネクションを生成
		MessagePrinter.stronglyPrintln("initializing db ... ");
		DBConnection.createInstance(settings.getDbPath());
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();

		MessagePrinter.stronglyPrintln("preparing the output file ... ");
		pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(
				settings.getCsvPath()))));
		MessagePrinter.stronglyPrintln("\tOK");
		MessagePrinter.stronglyPrintln();
	}

	/**
	 * メインの処理 (Training モード)
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void writeTrainingSet(final VectorWriterSettings settings)
			throws Exception {
		// クエリがデフォルトの場合，指定された番号のリビジョンのIDを復元してクエリに反映
		String query = settings.getQuery();
		if (settings.isDefaultQuery()) {
			final RevisionData startRevision = DBConnection
					.getInstance()
					.getRevisionRetriever()
					.getOldestRevisionAfterSpecifiedRevision(
							settings.getStartRevision());
			final RevisionData endRevision = DBConnection
					.getInstance()
					.getRevisionRetriever()
					.getLatestRevisionBeforeSpecifiedRevision(
							settings.getEndRevision());

			query = "select * from VECTOR_LINK where BEFORE_REVISION_ID >= "
					+ startRevision.getId() + " and BEFORE_REVISION_ID <= "
					+ endRevision.getId();
		}

		// ベクトルペア情報を復元
		MessagePrinter.stronglyPrint("retrieving vector pairs");
		MessagePrinter.print(" with \"" + query + "\"");
		MessagePrinter.stronglyPrintln(" ... ");
		final Set<VectorPairData> vectorPairs = DBConnection.getInstance()
				.getVectorPairRetriever().retrieve(query);
		MessagePrinter.stronglyPrintln("\t" + vectorPairs.size()
				+ " pairs were retrieved");
		MessagePrinter.stronglyPrintln();

		// 復元したベクトルペアに含まれるベクトル情報を復元
		MessagePrinter.stronglyPrintln("retrieving vectors ... ");
		final Set<Long> vectorIdsToBeRetrieved = new TreeSet<Long>();

		for (final VectorPairData vectorPair : vectorPairs) {
			vectorIdsToBeRetrieved.add(vectorPair.getBeforeVectorId());
			vectorIdsToBeRetrieved.add(vectorPair.getAfterVectorId());
		}

		final Set<VectorData> vectors = DBConnection.getInstance()
				.getVectorRetriever().retrieveWithIds(vectorIdsToBeRetrieved);

		// 復元したベクトル集合をマップに変換
		final Map<Long, VectorData> vectorsMap = new TreeMap<Long, VectorData>();
		for (final VectorData vector : vectors) {
			vectorsMap.put(vector.getId(), vector);
		}

		MessagePrinter.stronglyPrintln("\t" + vectorsMap.size()
				+ " vectors were retrieved");
		MessagePrinter.stronglyPrintln();

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		final List<Integer> ignoreList = settings.getIgnoreList();

		// ヘッダを出力
		pw.println(VectorData.getTrainingCsvHeader(ignoreList));

		// 各レコードを出力
		for (final VectorPairData vectorPair : vectorPairs) {
			final VectorData beforeVector = vectorsMap.get(vectorPair
					.getBeforeVectorId());
			final VectorData afterVector = vectorsMap.get(vectorPair
					.getAfterVectorId());

			pw.println(beforeVector.toCsvRecord(ignoreList) + ","
					+ afterVector.toCsvRecord(ignoreList));
		}
		MessagePrinter.stronglyPrintln("\tcomplete!!");
	}

	/**
	 * メインの処理 (Evaluation モード)
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void writeEvaluationSet(final VectorWriterSettings settings)
			throws Exception {
		// メソッド情報を復元
		MessagePrinter.stronglyPrintln("retrieving methods ... ");

		final Set<MethodData> methods = new TreeSet<MethodData>();

		if (settings.isDefaultQuery()) {
			final Set<RevisionData> revisions = DBConnection
					.getInstance()
					.getRevisionRetriever()
					.retrieveRevisionsInSpecifiedRange(
							settings.getStartRevision(),
							settings.getEndRevision());

			final MethodDataRetriever methodRetriever = DBConnection
					.getInstance().getMethodRetriever();
			for (final RevisionData revision : revisions) {
				methods.addAll(methodRetriever
						.retrieveInSpecifiedRevision(revision.getId()));
			}
		} else {
			methods.addAll(DBConnection.getInstance().getMethodRetriever()
					.retrieve(settings.getQuery()));
		}

		MessagePrinter.stronglyPrintln("\t" + methods.size()
				+ " methods were retrieved");

		// 復元すべきベクトルのIDを取得
		final Set<Long> vectorIds = new TreeSet<Long>();
		for (final MethodData method : methods) {
			vectorIds.add(method.getVectorId());
		}
		MessagePrinter.stronglyPrintln();

		final List<Integer> ignoreList = settings.getIgnoreList();

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		// ヘッダを出力
		pw.println(VectorData.getEvaluationCsvHeader(ignoreList));

		final Set<VectorData> vectors = DBConnection.getInstance()
				.getVectorRetriever().retrieveWithIds(vectorIds);

		for (final VectorData vector : vectors) {
			pw.println(vector.toCsvRecord(ignoreList));
		}

		MessagePrinter.stronglyPrintln("\tcomplete!");
	}

	/**
	 * 後処理を行う
	 */
	private static void postprocess() {
		if (DBConnection.getInstance() != null) {
			DBConnection.getInstance().close();
		}
		if (pw != null) {
			pw.close();
		}
	}

}
