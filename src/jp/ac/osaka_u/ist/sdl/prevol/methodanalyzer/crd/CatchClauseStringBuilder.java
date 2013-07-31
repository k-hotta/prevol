package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.CatchClause;

/**
 * CatchClause に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class CatchClauseStringBuilder extends
		AbstractBlockStringBuilder<CatchClause> {

	public CatchClauseStringBuilder(CatchClause node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("CATCH,"); // 文の種類

		// catch する例外の型
		builder.append(node.getException().getType().toString());

		return builder.toString();
	}

}
