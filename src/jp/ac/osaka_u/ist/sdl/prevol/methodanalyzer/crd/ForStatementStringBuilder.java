package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.ForStatement;

/**
 * ForStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class ForStatementStringBuilder extends
		AbstractBlockStringBuilder<ForStatement> {

	public ForStatementStringBuilder(ForStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("FOR,"); // ブロックの種類
		if (node.getExpression() != null) {
			builder.append(node.getExpression().toString()); // 条件式
		}

		return builder.toString();
	}

}
