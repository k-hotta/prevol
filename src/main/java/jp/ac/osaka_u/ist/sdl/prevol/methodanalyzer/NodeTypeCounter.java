package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import java.util.HashMap;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.VectorData;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;

/**
 * あるメソッドに含まれるノードの数をカウントするクラス
 * 
 * @author k-hotta
 * 
 */
public class NodeTypeCounter extends ASTVisitor {

	/**
	 * ベクトルデータ <br>
	 * キー：　ASTNodeの各子クラスに割り当てられた整数 <br>
	 * 値：　そのノードの出現数
	 */
	private final Map<Integer, Integer> vector;

	/**
	 * コンストラクタ
	 */
	public NodeTypeCounter() {
		this.vector = new HashMap<Integer, Integer>();
	}

	/**
	 * 解析結果を取得する
	 * 
	 * @return
	 */
	public VectorData getVectorData() {
		return new VectorData(vector);
	}

	@Override
	public void preVisit(ASTNode node) {
		final int nodeType = node.getNodeType();

		if (vector.containsKey(nodeType)) {
			final int nextNodeCount = vector.get(nodeType) + 1;
			vector.remove(nodeType);
			vector.put(nodeType, nextNodeCount);
		} else {
			vector.put(nodeType, 1);
		}
	}

}
