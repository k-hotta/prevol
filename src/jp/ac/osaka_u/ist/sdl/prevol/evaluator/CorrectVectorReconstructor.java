package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

/**
 * 正解データ arff ファイルから情報を復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class CorrectVectorReconstructor {

	private final String correctFilePath;

	public CorrectVectorReconstructor(final String correctFilePath) {
		this.correctFilePath = correctFilePath;
	}

	public Map<Integer, Map<NodeType, Integer>> reconstruct() throws Exception {
		final Map<Integer, Map<NodeType, Integer>> result = new TreeMap<Integer, Map<NodeType, Integer>>();

		final ArffReader reader = new ArffReader(correctFilePath);
		reader.load();

		final Map<Integer, String> attributes = reader.getAttributes();

		for (final Map.Entry<Integer, Map<Integer, Integer>> row : reader
				.getRows().entrySet()) {
			final int rowNumber = row.getKey();
			final Map<Integer, Integer> rowValues = row.getValue();

			final int startIndex = rowValues.size() / 2;
			final int endIndex = rowValues.size() - 1;

			final Map<NodeType, Integer> reconstructedVector = new HashMap<NodeType, Integer>();
			for (int i = startIndex; i <= endIndex; i++) {
				final String attributeStr = attributes.get(i).substring(
						"AFTER_".length());
				reconstructedVector.put(NodeType.getNodeType(attributeStr),
						rowValues.get(i));
			}

			result.put(rowNumber, reconstructedVector);
		}

		return Collections.unmodifiableMap(result);
	}

}
