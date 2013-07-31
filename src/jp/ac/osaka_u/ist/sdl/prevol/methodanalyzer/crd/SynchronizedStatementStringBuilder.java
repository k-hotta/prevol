package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.SynchronizedStatement;

/**
 * SynchronizedStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class SynchronizedStatementStringBuilder extends
		AbstractBlockStringBuilder<SynchronizedStatement> {

	public SynchronizedStatementStringBuilder(SynchronizedStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();
		
		builder.append("SYNCHRONIZED,"); // 文の種類
		builder.append(node.getExpression()); // 式
		
		return builder.toString();
	}

}
