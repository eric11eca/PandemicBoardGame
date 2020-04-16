package test.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.GameProperty;
import test.MockGameProperty;

public class TestMockGameProperty {
	MockGameProperty uut = new MockGameProperty();

	@Before
	@After
	public void resetProperty() {
		uut.resetAndEject();
	}

	@Test
	public void testBasicString() {
		uut.put("KEY_1", "Hello World!").inject();
		assertEquals("Hello World!", GameProperty.getInstance().get("KEY_1"));
	}

	@Test
	public void testBasicInt() {
		uut.put("KEY_2", "123").inject();
		assertEquals(123, GameProperty.getInstance().getInt("KEY_2"));
	}

	@Test
	public void testBasicIntArr() {
		uut.put("KEY_3", "123,456,789").inject();
		assertArrayEquals(new int[] { 123, 456, 789 }, GameProperty.getInstance().getIntArray("KEY_3"));
	}

	@Test
	public void testMultipleStrings() {//@formatter:off
		uut.put("KEY_4", "String1")
		   .put("KEY_5", "String2")
		   .inject();
		//@formatter:on
		assertEquals("String1", GameProperty.getInstance().get("KEY_4"));
		assertEquals("String2", GameProperty.getInstance().get("KEY_5"));
	}

	@Test
	public void testMultipleInts() {//@formatter:off
		uut.put("KEY_6", "123")
		   .put("KEY_7", "724")
		   .inject();
		//@formatter:on
		assertEquals(724, GameProperty.getInstance().getInt("KEY_7"));
		assertEquals(123, GameProperty.getInstance().getInt("KEY_6"));
	}

	@Test
	public void testMultipleIntArrs() {//@formatter:off
		uut.put("KEY_8", "123,456,789")
		   .put("KEY_9", "36,1,0")
		   .inject();
		//@formatter:on
		assertArrayEquals(new int[] { 123, 456, 789 }, GameProperty.getInstance().getIntArray("KEY_8"));
		assertArrayEquals(new int[] { 36, 1, 0 }, GameProperty.getInstance().getIntArray("KEY_9"));
	}

	@Test
	public void testMixed() {//@formatter:off
		uut.put("KEY_10", "123")
		   .put("KEY_11", "724")
		   .put("KEY_12", "1,2,3,4,5")
		   .put("KEY_13", "hahaha")
		   .put("KEY_14", "what")
		   .inject();
		//@formatter:on
		assertEquals(123, GameProperty.getInstance().getInt("KEY_10"));
		assertEquals("123", GameProperty.getInstance().get("KEY_10"));
		assertEquals(724, GameProperty.getInstance().getInt("KEY_11"));
		assertEquals("724", GameProperty.getInstance().get("KEY_11"));
		assertEquals("1,2,3,4,5", GameProperty.getInstance().get("KEY_12"));
		assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, GameProperty.getInstance().getIntArray("KEY_12"));
		assertEquals("hahaha", GameProperty.getInstance().get("KEY_13"));
		assertEquals("what", GameProperty.getInstance().get("KEY_14"));
	}

	@Test(expected = NullPointerException.class)
	public void testNonExist() {//@formatter:off
		uut.put("KEY_10", "123")
		   .put("KEY_11", "724")
		   .put("KEY_12", "1,2,3,4,5")
		   .put("KEY_13", "hahaha")
		   .put("KEY_14", "what")
		   .inject();
		//@formatter:on
		GameProperty.getInstance().get("DOES NOT EXIST");
	}

	public void testFromFile() throws FileNotFoundException {
		uut.injectFile(new File("testdata/test.properties"));
		assertEquals(123, GameProperty.getInstance().getInt("KEY_10"));
		assertEquals("123", GameProperty.getInstance().get("KEY_10"));
		assertEquals(724, GameProperty.getInstance().getInt("KEY_11"));
		assertEquals("724", GameProperty.getInstance().get("KEY_11"));
		assertEquals("1,2,3,4,5", GameProperty.getInstance().get("KEY_12"));
		assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, GameProperty.getInstance().getIntArray("KEY_12"));
		assertEquals("hahaha", GameProperty.getInstance().get("KEY_13"));
		assertEquals("what", GameProperty.getInstance().get("KEY_14"));
	}

	@Test(expected = NullPointerException.class)
	public void testFromFileNonExist() throws FileNotFoundException {
		uut.injectFile(new File("testdata/test.properties"));
		GameProperty.getInstance().get("DOES NOT EXIST");
	}
}
