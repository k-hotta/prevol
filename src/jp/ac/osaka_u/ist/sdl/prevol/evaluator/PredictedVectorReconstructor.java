package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

/**
 * 予測結果ベクトルを復元するクラス
 * 
 * @author k-hotta
 * 
 */
public class PredictedVectorReconstructor {

	/**
	 * 予測結果 arff ファイルが置かれているディレクトリへのパス
	 */
	private final String resultDirPath;

	public PredictedVectorReconstructor(final String resultDirPath) {
		this.resultDirPath = resultDirPath;
	}

	public Map<Integer, SortedMap<NodeType, Integer>> reconstruct()
			throws Exception {
		final Map<Integer, SortedMap<NodeType, Integer>> result = new TreeMap<Integer, SortedMap<NodeType, Integer>>();

		final File resultDir = new File(resultDirPath);
		if (!resultDir.isDirectory()) {
			return null; // ディレクトリじゃなければ何かがおかしい
		}

		// arff ファイルの読み込み
		final Map<NodeType, Map<Integer, Integer>> loadedNodeTypes = loadFiles(resultDir);
		if (loadedNodeTypes.isEmpty()) {
			return null; // 何も読み込めなかった場合もなにかおかしい
		}

		// 読み込んだ結果を所定のフォーマットに変換
		for (final Map.Entry<NodeType, Map<Integer, Integer>> nodeTypeEntry : loadedNodeTypes
				.entrySet()) {
			final NodeType type = nodeTypeEntry.getKey();
			final Map<Integer, Integer> values = nodeTypeEntry.getValue();

			for (final Map.Entry<Integer, Integer> value : values.entrySet()) {
				final int row = value.getKey();
				final int numberOfNodes = value.getValue();

				if (result.containsKey(row)) {
					result.get(row).put(type, numberOfNodes);
				} else {
					final SortedMap<NodeType, Integer> newRowMap = new TreeMap<NodeType, Integer>();
					newRowMap.put(type, numberOfNodes);
					result.put(row, newRowMap);
				}
			}
		}

		return Collections.unmodifiableMap(result);
	}

	/**
	 * ディレクトリ以下のarffファイルを読み込む
	 * 
	 * @param resultDir
	 * @return
	 * @throws Exception
	 */
	private final Map<NodeType, Map<Integer, Integer>> loadFiles(
			final File resultDir) throws Exception {
		final Map<NodeType, Map<Integer, Integer>> loadedNodeTypes = new HashMap<NodeType, Map<Integer, Integer>>();

		for (final File resultFile : resultDir.listFiles()) {
			final String resultFileName = resultFile.getName();

			if (!resultFileName.endsWith(".arff") || !resultFile.isFile()) {
				// .arff ファイルでなければ無視
				continue;
			}

			// ファイル名を元に対応するノードタイプを取得
			final NodeType attribute = NodeType
					.getNodeTypeWithFileName(resultFileName);

			if (attribute != null) {
				final Map<Integer, Integer> values = loadFile(resultFile
						.getAbsolutePath());
				loadedNodeTypes.put(attribute, values);
			}

		}

		return Collections.unmodifiableMap(loadedNodeTypes);
	}

	/**
	 * 指定されたarffファイルを読み込み，最終列の値のみを抽出
	 * 
	 * @param target
	 * @return
	 * @throws Exception
	 */
	private final Map<Integer, Integer> loadFile(final String target)
			throws Exception {
		final ArffReader reader = new ArffReader(target);
		reader.load();

		final Map<Integer, Integer> result = new TreeMap<Integer, Integer>();

		final Map<Integer, Map<Integer, Integer>> rows = reader.getRows();
		for (final Map.Entry<Integer, Map<Integer, Integer>> row : rows
				.entrySet()) {
			final int rowNumber = row.getKey();
			final int lastValue = row.getValue().get(row.getValue().size() - 1);

			result.put(rowNumber, lastValue);
		}

		return Collections.unmodifiableMap(result);
	}

}
