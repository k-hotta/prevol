package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Map;

import jp.ac.osaka_u.ist.sdl.prevol.data.NodeType;

import org.junit.BeforeClass;
import org.junit.Test;

public class PredictedVectorReconstructorTest {

	private static Map<Integer, Map<NodeType, Integer>> reconstructedVectors;

	@BeforeClass
	public static void setup() throws Exception {
		final String target = "test-resources" + File.separator + "test";
		final PredictedVectorReconstructor reconstructor = new PredictedVectorReconstructor(
				target);
		reconstructedVectors = reconstructor.reconstruct();
	}

	@Test
	public void test() {
		assertTrue(reconstructedVectors.get(0).size() == 55);
	}

	@Test
	public void test2() {
		int valuesCount = -1;
		for (final Map.Entry<Integer, Map<NodeType, Integer>> entry : reconstructedVectors
				.entrySet()) {
			if (valuesCount == -1) {
				valuesCount = entry.getValue().size();
			} else if (valuesCount != entry.getValue().size()) {
				fail();
			}
		}
		assertTrue(true);
	}

}
