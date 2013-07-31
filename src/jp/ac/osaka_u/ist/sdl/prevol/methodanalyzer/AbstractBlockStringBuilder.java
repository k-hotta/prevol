package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import org.eclipse.jdt.core.dom.ASTNode;

/**
 * 各ブロックに対してそのブロックを表す文字列を生成するための抽象クラス
 * 
 * @author k-hotta
 * 
 */
public abstract class AbstractBlockStringBuilder<T extends ASTNode> {

	/**
	 * 着目中のブロックを表すASTNode
	 */
	protected final T node;
	
	public AbstractBlockStringBuilder(final T node) {
		this.node = node;
	}
	
	public abstract String getBlockStr();
	
}
