package jp.ac.osaka_u.ist.sdl.prevol.data.vectorwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.jdt.core.dom.ASTNode;

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
			} else if (settings.getMode() == VectorWriterMode.SINGLE_COLUMN_TRAINING) {
				writeAllSingleColumnTrainingSet(settings);
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

		if (settings.getMode() != VectorWriterMode.SINGLE_COLUMN_TRAINING) {
			MessagePrinter.stronglyPrintln("preparing the output file ... ");
			pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(
					settings.getOutputFilePath()))));
			MessagePrinter.stronglyPrintln("\tOK");
			MessagePrinter.stronglyPrintln();
		}
	}

	/**
	 * メインの処理 (SingleColumnTraining モード)
	 * 
	 * @param settings
	 * @throws Exception
	 */
	private static void writeAllSingleColumnTrainingSet(
			final VectorWriterSettings settings) throws Exception {
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
		final Set<Integer> zeroColumns = VectorData.getNodeTypeIntegers();

		for (final VectorData vector : vectors) {
			vectorsMap.put(vector.getId(), vector);
			final Set<Integer> nonzeroColumns = vector
					.getElementContainingColumns();
			zeroColumns.removeAll(nonzeroColumns);
		}

		MessagePrinter.stronglyPrintln("\t" + vectorsMap.size()
				+ " vectors were retrieved");
		MessagePrinter.stronglyPrintln();

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		final List<Integer> ignoreList = new ArrayList<Integer>();
		ignoreList.addAll(settings.getIgnoreList());
		ignoreList.addAll(zeroColumns);

		final File specifiedFile = new File(settings.getOutputFilePath());
		final int suffixLength = (settings.getOutputFileFormat() == OutputFileFormat.CSV) ? 4
				: 5;
		final String specifiedFileName = specifiedFile.getName().substring(0,
				specifiedFile.getName().length() - suffixLength);
		final String outputDir = specifiedFile.getParentFile()
				.getAbsolutePath();
		final String suffix = (settings.getOutputFileFormat() == OutputFileFormat.CSV) ? ".csv"
				: ".arff";

		for (final int nodeType : VectorData.getNodeTypeIntegers()) {
			if (ignoreList.contains(nodeType)) {
				continue;
			}

			final String outputFileName = specifiedFileName + "-"
					+ ASTNode.nodeClassForType(nodeType).getSimpleName()
					+ suffix;
			final String outputFilePath = outputDir + File.separator
					+ outputFileName;
			final PrintWriter pw = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(outputFilePath))));

			// ヘッダを出力
			if (settings.getOutputFileFormat() == OutputFileFormat.CSV) {
				pw.println(VectorData.getSingleColumnTrainingCsvHeader(
						ignoreList, ASTNode.nodeClassForType(nodeType)
								.getSimpleName()));
			} else {
				pw.println(VectorData.getSingleColumnTrainingArffHeader(
						ignoreList, settings.getRelationName()
								+ "-"
								+ ASTNode.nodeClassForType(nodeType)
										.getSimpleName(), ASTNode
								.nodeClassForType(nodeType).getSimpleName()));
			}

			// 各レコードを出力
			for (final VectorPairData vectorPair : vectorPairs) {
				final VectorData beforeVector = vectorsMap.get(vectorPair
						.getBeforeVectorId());
				final VectorData afterVector = vectorsMap.get(vectorPair
						.getAfterVectorId());

				final Map<Integer, Integer> afterRawVector = afterVector
						.getVector();
				final int afterValue = (afterRawVector.containsKey(nodeType)) ? afterRawVector
						.get(nodeType) : 0;

				pw.println(beforeVector.toCsvRecord(ignoreList) + ","
						+ afterValue);
			}

			pw.close();
		}

		MessagePrinter.stronglyPrintln("\tcomplete!!");
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
		final Set<Integer> zeroColumns = VectorData.getNodeTypeIntegers();

		for (final VectorData vector : vectors) {
			vectorsMap.put(vector.getId(), vector);
			final Set<Integer> nonzeroColumns = vector
					.getElementContainingColumns();
			zeroColumns.removeAll(nonzeroColumns);
		}

		MessagePrinter.stronglyPrintln("\t" + vectorsMap.size()
				+ " vectors were retrieved");
		MessagePrinter.stronglyPrintln();

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		final List<Integer> ignoreList = new ArrayList<Integer>();
		ignoreList.addAll(settings.getIgnoreList());
		ignoreList.addAll(zeroColumns);

		// ヘッダを出力
		if (settings.getOutputFileFormat() == OutputFileFormat.CSV) {
			pw.println(VectorData.getTrainingCsvHeader(ignoreList));
		} else {
			pw.println(VectorData.getTrainingArffHeader(ignoreList,
					settings.getRelationName()));
		}

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
				MessagePrinter.println("\tprocessing "
						+ revision.getRevisionNum());
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

		// ベクトルを復元
		final Set<Integer> zeroColumns = VectorData.getNodeTypeIntegers();
		final Set<VectorData> vectors = DBConnection.getInstance()
				.getVectorRetriever().retrieveWithIds(vectorIds);
		for (final VectorData vector : vectors) {
			final Set<Integer> nonzeroColumns = vector
					.getElementContainingColumns();
			zeroColumns.removeAll(nonzeroColumns);
			if (zeroColumns.isEmpty()) {
				break;
			}
		}

		final List<Integer> ignoreList = new ArrayList<Integer>();
		ignoreList.addAll(settings.getIgnoreList());
		ignoreList.addAll(zeroColumns);

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		// ヘッダを出力
		if (settings.getOutputFileFormat() == OutputFileFormat.CSV) {
			pw.println(VectorData.getEvaluationCsvHeader(ignoreList));
		} else {
			pw.println(VectorData.getEvaluationArffHeader(ignoreList,
					settings.getRelationName()));
		}

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
