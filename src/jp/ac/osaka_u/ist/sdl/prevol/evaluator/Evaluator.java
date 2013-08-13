package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.util.Map;
import java.util.SortedMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;
import jp.ac.osaka_u.ist.sdl.prevol.utils.MessagePrinter;

/**
 * 予測精度の評価を行うためのクラス
 * 
 * @author k-hotta
 * 
 */
public class Evaluator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			final EvaluatorSettings settings = EvaluatorSettings
					.parseArgs(args);

			// 出力レベルを設定
			MessagePrinter.setMode(settings.getPrintMode());

			MessagePrinter.stronglyPrintln("operations start");
			MessagePrinter.stronglyPrintln();

			// 正解ベクトルを復元
			MessagePrinter.stronglyPrintln("loading correct vectors ...");
			MessagePrinter.println("\tfile path: "
					+ settings.getCorrectDataFilePath());

			final CorrectVectorReconstructor correctReconstructor = new CorrectVectorReconstructor(
					settings.getCorrectDataFilePath());
			Map<Integer, SortedMap<NodeType, Integer>> correctVectors = correctReconstructor
					.reconstruct();

			MessagePrinter.stronglyPrintln("\t" + correctVectors.size()
					+ " vectors have been loaded");
			MessagePrinter.stronglyPrintln();

			// 予測ベクトルを復元
			MessagePrinter.stronglyPrintln("loading predicted vectors ...");
			MessagePrinter.println("\tdirectory path: "
					+ settings.getCorrectDataFilePath());

			final PredictedVectorReconstructor predictedReconstructor = new PredictedVectorReconstructor(
					settings.getPredictedResultDir());
			Map<Integer, SortedMap<NodeType, Integer>> predictedVectors = predictedReconstructor
					.reconstruct();

			MessagePrinter.stronglyPrintln("\t" + predictedVectors.size()
					+ " vectors have been loaded");
			MessagePrinter.stronglyPrintln();

			// 予測ベクトルの数と正解ベクトルの数が同じであることを確認
			MessagePrinter
					.stronglyPrintln("checking the number of loaded vectors ...");
			if (correctVectors.size() != predictedVectors.size()) {
				throw new IllegalStateException(
						"FATAL ERROR: the numbers of correct vectors and predicted ones do not match");
			}
			MessagePrinter.stronglyPrintln("\tok");
			MessagePrinter.stronglyPrintln();

			// 出力
			MessagePrinter.stronglyPrintln("writing a result file ...");
			MessagePrinter.println("\tfile path: "
					+ settings.getOutputFilePath());

			final EvaluateResultWriter writer = new EvaluateResultWriter(
					settings.getOutputFilePath(), correctVectors,
					predictedVectors);
			writer.write();
			MessagePrinter.stronglyPrintln();
			
			MessagePrinter.stronglyPrintln("complete!!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
