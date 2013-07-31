package jp.ac.osaka_u.ist.sdl.prevol.methodanalyzer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.BeforeClass;
import org.junit.Test;

public class ASTAnalyzerTest {

	private static CompilationUnit root = null;

	@BeforeClass
	public static void testSetUp() throws Exception {
		final String testTarget = "test-resources" + File.separator + "simple"
				+ File.separator + "BlockDetector.java";
		final BufferedReader br = new BufferedReader(new FileReader(new File(
				testTarget)));
		final StringBuilder builder = new StringBuilder();

		String line;

		while ((line = br.readLine()) != null) {
			builder.append(line + System.getProperty("line.separator"));
		}

		root = ASTCreator.createAST(builder.toString());
		
		br.close();
	}

	@Test
	public void test() {
		final ASTAnalyzer analyzer = new ASTAnalyzer(1, "A.java");
		root.accept(analyzer);
		assertTrue(analyzer.getMethods().size() == 5);
	}

}
