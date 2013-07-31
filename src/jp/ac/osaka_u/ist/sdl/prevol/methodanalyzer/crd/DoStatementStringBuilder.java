package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.DoStatement;

/**
 * DoStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class DoStatementStringBuilder extends
		AbstractBlockStringBuilder<DoStatement> {

	public DoStatementStringBuilder(DoStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("DO,"); // 文の種類
		builder.append(node.getExpression()); // 条件式

		return builder.toString();
	}

}
