package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

/**
 * ベクトルを比較し，ベクトル間の差分を算出するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorsComparator {

	public static int calcVectorDistance(final Map<NodeType, Integer> v1,
			final Map<NodeType, Integer> v2) {
		assert v1.size() == v2.size();

		int result = 0;

		for (final Map.Entry<NodeType, Integer> entryV1 : v1.entrySet()) {
			if (!(v2.containsKey(entryV1.getKey()) && (v2.get(entryV1.getKey()) == entryV1
					.getValue()))) {
				result++;
			}
		}

		return result;
	}

}
