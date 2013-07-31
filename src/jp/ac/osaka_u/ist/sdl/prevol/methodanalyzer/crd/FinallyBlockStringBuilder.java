package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import java.util.List;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;

/**
 * finally節に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class FinallyBlockStringBuilder extends
		AbstractBlockStringBuilder<Block> {

	private static final String DIVIDER = " ";

	public FinallyBlockStringBuilder(Block node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("FINALLY,"); // 文の種類

		// 親のTry文が catch する例外の型を連結
		final TryStatement parentTryStatement = (TryStatement) node.getParent();

		@SuppressWarnings("rawtypes")
		List catchClauses = parentTryStatement.catchClauses();

		boolean catchAnyException = false;

		for (Object obj : catchClauses) {
			final CatchClause catchClause = (CatchClause) obj;
			final String caughtExceptionType = catchClause.getException()
					.getType().toString();
			builder.append(caughtExceptionType + DIVIDER);
			catchAnyException = true;
		}

		// 最後の DIVIDER は不要
		if (catchAnyException) {
			builder.delete(builder.length() - DIVIDER.length(),
					builder.length());
		}

		return builder.toString();
	}

}
