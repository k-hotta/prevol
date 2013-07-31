package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.IfStatement;

/**
 * IfStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class IfStatementStringBuilder extends
		AbstractBlockStringBuilder<IfStatement> {

	public IfStatementStringBuilder(IfStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();
		
		builder.append("IF,"); // 文の種類
		builder.append(node.getExpression());
		
		return builder.toString();
	}

}
