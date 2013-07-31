package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.EnhancedForStatement;

/**
 * EnhancedForStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class EnhancedForStatementStringBuilder extends
		AbstractBlockStringBuilder<EnhancedForStatement> {

	public EnhancedForStatementStringBuilder(EnhancedForStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("FOR,"); // ブロックの種類
		builder.append(node.getExpression()); // 条件式

		return builder.toString();
	}

}
