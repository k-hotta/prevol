package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

/**
 * TypeDeclaration に対する文字列を生成するクラス
 * 
 * @author k-hotta
 * 
 */
public class TypeDeclarationStringBuilder extends
		AbstractBlockStringBuilder<TypeDeclaration> {

	public TypeDeclarationStringBuilder(TypeDeclaration node) {
		super(node);
	}

	@Override
	public String getBlockStr() {
		// インターフェースは考えない
		if (node.isInterface()) {
			assert false;
			return "";
		}
		
		final String fullyQualifiedName = getFullyQualifiedName(node);
		
		return fullyQualifiedName;
	}

	/**
	 * 完全限定名を取得する
	 * 
	 * @param node
	 * @return
	 */
	private final String getFullyQualifiedName(ASTNode node) {
		final StringBuilder builder = new StringBuilder();
		detectFullyQualifiedName(node, builder);
		return builder.toString();
	}

	/**
	 * 完全限定名を特定する <br>
	 * node の型による呼び出し先メソッドの振り分けを行う <br>
	 * TypeDeclaration, CompilationUnit の時はそれに応じたメソッドを呼び出し， <br>
	 * それ以外の場合は無視して親ノードを処理する <br>
	 * 
	 * @param node
	 * @param builder
	 */
	private void detectFullyQualifiedName(ASTNode node, StringBuilder builder) {
		if (node instanceof TypeDeclaration) {
			detectFullyQualifiedName((TypeDeclaration) node, builder);
		} else if (node instanceof CompilationUnit) {
			detectFullyQualifiedName((CompilationUnit) node, builder);
		} else {
			detectFullyQualifiedName(node.getParent(), builder);
		}
	}

	private void detectFullyQualifiedName(TypeDeclaration node,
			StringBuilder builder) {
		builder.insert(0, node.getName());
		detectFullyQualifiedName(node.getParent(), builder);
	}

	private void detectFullyQualifiedName(CompilationUnit node,
			StringBuilder builder) {
		if (node.getPackage() != null) {
			builder.insert(0, node.getPackage().getName() + ".");
		}
	}

}
