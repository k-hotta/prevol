package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;

/**
 * ベクトルを比較し，ベクトル間の差分を算出するクラス
 * 
 * @author k-hotta
 * 
 */
public class VectorsComparator {

	public static int calcNotMatchingNodeTypeCount(final VectorData v1, final VectorData v2) {
		int result = 0;
		int comparedNodeTypes = 0;
		
		final Map<Integer, Integer> nodeMapInV2 = v2.getVector();
		
		// v1のマップに含まれる(=v1におけるノード数が0ではない)ノードタイプについて
		// v2にそれが含まれているか，含まれてれば数があっているかを比較
		// いずれも満たさなければ，数が違うので result をインクリメント
		for  (final Map.Entry<Integer, Integer> entryInV1 : v1.getVector().entrySet()) {
			final int nodeTypeContainedInV1 = entryInV1.getKey();
			final int nodeCountInV1 = entryInV1.getValue();
			
			if (nodeMapInV2.containsKey(nodeTypeContainedInV1)) {
				final int nodeCountInV2 = nodeMapInV2.get(nodeTypeContainedInV1);
				comparedNodeTypes++;
				if (nodeCountInV1 != nodeCountInV2) {
					result++;
				}
			}
		}
		
		// v2のマップに含まれるノードタイプについて，v1との比較がされていないものについては
		// v1にそれがない，つまりそのノードタイプのノード数がv1とv2で違うので，
		// その分を result に追加
		result += nodeMapInV2.size() - comparedNodeTypes;
		
		return result;
	}

}
