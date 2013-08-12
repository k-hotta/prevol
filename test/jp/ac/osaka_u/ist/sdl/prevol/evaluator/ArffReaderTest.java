package jp.ac.osaka_u.ist.sdl.prevol.evaluator;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ArffReaderTest {

	private static ArffReader reader;

	@BeforeClass
	public static void initializeReader() {
		reader = new ArffReader("test-resources" + File.separator + "arff"
				+ File.separator + "ant.arff");
		try {
			reader.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Ignore
	public void testLoad() {
		try {
			reader.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void test1() {
		assertTrue(reader.getAttributes().size() == 110);
	}

	@Test
	public void test2() {
		assertTrue(reader.getRows().size() == 119318);
	}
	
	@Test
	public void test3() {
		assertTrue(reader.getAttributes().get(7).equals("BEFORE_BOOLEAN_LITERAL"));
	}

	@Test
	public void test4() {
		assertTrue(reader.getRows().get(0).size() == 110);
	}
	
	@Test
	public void test5() {
		assertTrue(reader.getRows().get(5).get(5) == 1);
	}
	
}
