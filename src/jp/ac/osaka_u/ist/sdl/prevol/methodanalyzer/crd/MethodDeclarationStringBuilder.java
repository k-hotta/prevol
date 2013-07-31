package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer.crd;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;

/**
 * MethodDeclaration に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class MethodDeclarationStringBuilder extends
		AbstractBlockStringBuilder<MethodDeclaration> {

	public MethodDeclarationStringBuilder(MethodDeclaration node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		final String signature = detectCanonicalSignature(node);

		return signature;
	}

	/**
	 * 宣言されているメソッドのシグネチャを特定する
	 * 
	 * @param node
	 * @return
	 */
	private String detectCanonicalSignature(MethodDeclaration node) {
		StringBuilder builder = new StringBuilder();

		builder.append(node.getName().toString());

		builder.append("(");
		{
			boolean isFirstParam = true;
			for (Object obj : node.parameters()) {
				SingleVariableDeclaration param = (SingleVariableDeclaration) obj;
				if (!isFirstParam) {
					builder.append(", ");
				} else {
					isFirstParam = false;
				}
				builder.append(param.getType().toString());
			}
		}
		builder.append(")");

		return builder.toString();
	}

}
