package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * WhileStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class WhileStatementStringBuilder extends
		AbstractBlockStringBuilder<WhileStatement> {

	public WhileStatementStringBuilder(WhileStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();
		
		builder.append("WHILE,"); // 文の種類
		builder.append(node.getExpression()); // 条件式
		
		return builder.toString();
	}

}
