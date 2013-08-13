package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

/**
 * ベクトルを比較し，ベクトル間の差分を算出するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorsComparator {

	public static SortedMap<NodeType, Integer> calcVectorDistance(final SortedMap<NodeType, Integer> v1,
			final SortedMap<NodeType, Integer> v2) {
		assert v1.size() == v2.size();

		SortedMap<NodeType, Integer> result = new TreeMap<NodeType, Integer>();

		for (final Map.Entry<NodeType, Integer> entryV1 : v1.entrySet()) {
			assert v2.containsKey(entryV1.getKey());
			
			final NodeType type = entryV1.getKey();
			final int valueV1 = entryV1.getValue();
			final int valueV2 = v2.get(entryV1.getKey());
			
			result.put(type, valueV1 - valueV2);
		}

		return result;
	}

}
