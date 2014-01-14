package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import static org.junit.Assert.*;
import jp.ac.osaka_u.ist.sdl.prevol.data.RevisionData;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

public class ASTAnalyzerTest {

	@Test
	public void test() {
		final String source = "public class Test { \n private int n; \n private int y; \n public int min() { \n if (x <= y) { \n return x; \n } else { \n return y; \n } \n } \n }";
		final CompilationUnit rootNode = ASTCreator.createAST(source);

		// ASTをパース
		final ASTAnalyzer analyzer = new ASTAnalyzer(new RevisionData(1), new RevisionData(2), 0);
		rootNode.accept(analyzer);

		assertTrue(analyzer.getMethods().size() == 1);
	}
}
