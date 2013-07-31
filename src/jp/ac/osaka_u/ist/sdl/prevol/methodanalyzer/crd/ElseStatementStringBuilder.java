package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;

/**
 * else文(?)に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class ElseStatementStringBuilder extends
		AbstractBlockStringBuilder<Statement> {

	private static final String DIVIDER = " :: ";

	public ElseStatementStringBuilder(Statement node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final StringBuilder builder = new StringBuilder();

		builder.append("ELSE,"); // 文の種類

		// else 節の外側にある If 文の条件式を連結
		final List<String> predicates = detectElsePredicates(node);
		for (final String predicate : predicates) {
			builder.append(predicate + DIVIDER);
		}

		// 最後の DIVIDER は不要なので消去
		builder.delete(builder.length() - DIVIDER.length(), builder.length());

		return builder.toString();
	}

	/**
	 * else節用の条件式を特定する
	 * 
	 * @param elseStatement
	 * @return
	 */
	private List<String> detectElsePredicates(Statement elseStatement) {
		List<String> predicates = new LinkedList<String>();

		detectPredicates(elseStatement.getParent(), predicates);

		return predicates;
	}

	private void detectPredicates(ASTNode node, List<String> predicates) {
		if (!(node instanceof IfStatement)) {
			return;
		}

		detectPredicates(node.getParent(), predicates);

		IfStatement ifStatement = (IfStatement) node;
		predicates.add(ifStatement.getExpression().toString());
	}

}
