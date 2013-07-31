package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.SwitchStatement;

/**
 * SwitchStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class SwitchStatementStringBuilder extends
		AbstractBlockStringBuilder<SwitchStatement> {

	public SwitchStatementStringBuilder(SwitchStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();
		
		builder.append("SWITCH,"); // 文の種類
		builder.append(node.getExpression()); // 条件式
		
		return builder.toString();
	}

}
