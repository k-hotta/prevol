package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * あるファイルのASTを解析し，各メソッドのベクトルデータを計算するクラス
 * 
 * @author k-hotta
 * 
 */
public class ASTAnalyzer extends ASTVisitor {

	@Override
	public boolean visit(MethodDeclaration node) {
		final NodeTypeCounter counter = new NodeTypeCounter();
		node.accept(counter);
		
		return true;
	}
	
}
