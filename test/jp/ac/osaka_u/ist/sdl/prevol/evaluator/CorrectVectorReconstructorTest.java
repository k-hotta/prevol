package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

import org.junit.BeforeClass;
import org.junit.Test;

public class CorrectVectorReconstructorTest {

	private static Map<Integer, Map<NodeType, Integer>> reconstructedVectors;

	@BeforeClass
	public static void setup() throws Exception {
		final String target = "test-resources" + File.separator + "test"
				+ File.separator + "test.arff";
		final CorrectVectorReconstructor reconstructor = new CorrectVectorReconstructor(
				target);
		reconstructedVectors = reconstructor.reconstruct();
	}

	@Test
	public void test1() {
		assertTrue(reconstructedVectors.get(0).size() == reconstructedVectors
				.get(1).size());
	}

	@Test
	public void test2() {
		assertTrue(reconstructedVectors.get(0).size() == 55);
	}

	@Test
	public void test3() {
		assertTrue(reconstructedVectors.get(0).get(
				NodeType.VARIABLE_DECLARATION_FRAGMENT) == 10);
	}

	@Test
	public void test4() {
		assertTrue(reconstructedVectors.get(0).get(NodeType.SIMPLE_NAME) == 87);
	}

}
