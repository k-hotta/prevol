package jp.ac.osaka_u.ist.sdl.prevol.vectorwriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;
import jp.ac.osaka_u.ist.sdl.prevol.data.VectorPairData;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * SingleColumn 用の EvaluationSet (最後の一列だけ?になっているファイル)を出力するクラス
 * 
 * @author k-hotta
 * 
 */
public class SingleColumnEvaluationSetWriter extends AbstractWriter {

	public SingleColumnEvaluationSetWriter(VectorWriterSettings settings) {
		super(settings);
	}

	@Override
	public void write() throws Exception {
		// ベクトルペアを復元
		final Set<VectorPairData> vectorPairs = retrieveVectorPairs();

		// ベクトルを復元
		final Map<Long, VectorData> vectorsMap = retrieveVectorsInSpecifiedVectorPairs(vectorPairs);

		final List<Integer> ignoreList = getIgnoreColumnsListIncludingZeroColumns(vectorsMap
				.values());

		// 出力
		MessagePrinter.stronglyPrintln("printing the result ... ");

		final PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(new File(settings.getOutputFilePath()))));

		// ヘッダを出力
		if (settings.getOutputFileFormat() == OutputFileFormat.CSV) {
			pw.println(VectorData.getSingleColumnTrainingCsvHeader(ignoreList,
					"TO_BE_EVALUATED"));
		} else {
			pw.println(VectorData.getSingleColumnTrainingArffHeader(ignoreList,
					settings.getRelationName(), "TO_BE_EVALUATED"));
		}

		// 各レコードを出力
		for (final VectorPairData vectorPair : vectorPairs) {
			final VectorData beforeVector = vectorsMap.get(vectorPair
					.getBeforeVectorId());

			pw.println(beforeVector.toCsvRecord(ignoreList) + ",?");
		}

		MessagePrinter.stronglyPrintln("\tcomplete!!");

		pw.close();
	}

}
