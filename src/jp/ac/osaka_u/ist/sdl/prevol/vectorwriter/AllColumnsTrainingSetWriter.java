package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * TrainingSet を出力するクラス (すべてのカラムを単一ファイルに出力する)
 * 
 * @author k-hotta
 * 
 */
public class AllColumnsTrainingSetWriter extends AbstractWriter {

	public AllColumnsTrainingSetWriter(final VectorWriterSettings settings) {
		super(settings);
	}

	@Override
	public void write() throws Exception {
		// ベクトルペアを復元
		final Set<VectorPairData> vectorPairs = retrieveVectorPairs();

		// ベクトルを復元
		final Map<Long, VectorData> vectorsMap = retrieveVectorsInSpecifiedVectorPairs(vectorPairs);

		final List<Integer> ignoreList = getIgnoreColumnsList(false,
				vectorsMap.values());

		writeElements(vectorPairs, vectorsMap, ignoreList,
				settings.getOutputFilePath());
	}

	protected void writeElements(final Set<VectorPairData> vectorPairs,
			final Map<Long, VectorData> vectorsMap,
			final List<Integer> ignoreList, final String outputFilePath)
			throws IOException {
		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		final PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(outputFilePath))));

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

		pw.close();
	}

}
