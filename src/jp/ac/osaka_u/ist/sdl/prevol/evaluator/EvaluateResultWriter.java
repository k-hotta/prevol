package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

/**
 * 評価結果を出力するクラス
 * 
 * @author k-hotta
 * 
 */
public class EvaluateResultWriter {

	/**
	 * 出力先ファイル
	 */
	private final String outputFilePath;

	/**
	 * 正解ベクトル
	 */
	private final Map<Integer, SortedMap<NodeType, Integer>> correctVectors;

	/**
	 * 予測ベクトル
	 */
	private final Map<Integer, SortedMap<NodeType, Integer>> predictedVectors;

	public EvaluateResultWriter(final String outputFilePath,
			final Map<Integer, SortedMap<NodeType, Integer>> correctVectors,
			final Map<Integer, SortedMap<NodeType, Integer>> predictedVectors) {
		this.outputFilePath = outputFilePath;
		this.correctVectors = correctVectors;
		this.predictedVectors = predictedVectors;
	}

	public void write() throws Exception {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				new File(outputFilePath))));

		// ヘッダの出力
		pw.println(getHeader());

		final int numberOfVectors = correctVectors.size();

		for (int i = 0; i < numberOfVectors; i++) {
			final SortedMap<NodeType, Integer> correctVector = correctVectors
					.get(i);
			final SortedMap<NodeType, Integer> predictedVector = predictedVectors
					.get(i);

			pw.println(getLine(i, correctVector, predictedVector));
		}

		pw.close();
	}

	private String getHeader() throws Exception {
		final StringBuilder headerBuilder = new StringBuilder();

		headerBuilder.append("id,");
		for (final Map.Entry<Integer, SortedMap<NodeType, Integer>> entry : correctVectors
				.entrySet()) {
			final Set<NodeType> nodeTypes = entry.getValue().keySet();
			for (final NodeType nodeType : nodeTypes) {
				headerBuilder.append(nodeType.name() + ",");
			}
			break;
		}

		headerBuilder.append("#_not_match_attributes,#_not_match_absolute");

		return headerBuilder.toString();
	}

	private String getLine(final int ordinal,
			final SortedMap<NodeType, Integer> correctVector,
			final SortedMap<NodeType, Integer> predictedVector)
			throws Exception {
		final StringBuilder lineBuilder = new StringBuilder();

		lineBuilder.append(ordinal + ",");

		final SortedMap<NodeType, Integer> distance = VectorsComparator
				.calcVectorDistance(predictedVector, correctVector);

		int notMatchAttributes = 0;
		int notMatchAbsolute = 0;

		for (final Map.Entry<NodeType, Integer> entry : distance.entrySet()) {
			final int margin = entry.getValue();
			if (margin != 0) {
				notMatchAttributes++;
				notMatchAbsolute += margin;
			}
			lineBuilder.append(margin + ",");
		}

		lineBuilder.append(notMatchAttributes + ",");
		lineBuilder.append(notMatchAbsolute);

		return lineBuilder.toString();
	}

}
