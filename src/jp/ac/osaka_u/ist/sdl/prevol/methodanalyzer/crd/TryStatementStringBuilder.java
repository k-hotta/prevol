package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import java.util.List;

import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;

/**
 * TryStatement に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class TryStatementStringBuilder extends
		AbstractBlockStringBuilder<TryStatement> {

	private static final String DIVIDER = " ";

	public TryStatementStringBuilder(TryStatement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("TRY,"); // 文の種類

		@SuppressWarnings("rawtypes")
		List catchClauses = node.catchClauses();

		// catch する例外の型を連結
		for (Object obj : catchClauses) {
			final CatchClause catchClause = (CatchClause) obj;
			final String caughtExceptionType = catchClause.getException()
					.getType().toString();
			builder.append(caughtExceptionType + DIVIDER);
		}

		// 最後の DIVIDER は不要
		if (builder.length() > 0) {
			builder.delete(builder.length() - DIVIDER.length(),
					builder.length());
		}

		return builder.toString();
	}

}
