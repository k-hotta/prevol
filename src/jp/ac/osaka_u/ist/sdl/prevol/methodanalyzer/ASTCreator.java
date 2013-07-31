package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * ASTを構築するクラス
 * 
 * @author k-hotta
 * 
 */
public class ASTCreator {

	/**
	 * 引数で与えられたソースコードを解析し，ASTを構築する
	 * 
	 * @param sourceCode
	 * @return
	 */
	public static CompilationUnit createAST(final String sourceCode) {
		final ASTParser parser = ASTParser.newParser(AST.JLS3);

		parser.setSource(sourceCode.toCharArray());

		return (CompilationUnit) parser.createAST(new NullProgressMonitor());
	}

}
