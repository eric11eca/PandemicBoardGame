package test.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lang.I18n;

public class TestI18n {
	@Test
	public void testNormalString() {
		assertEquals(I18n.format("test.string"), "String");
	}

	@Test
	public void testFormatString() {
		assertEquals(I18n.format("test.format", "Format234"), "Format Format234");
	}

	@Test
	public void testMissing() {
		assertEquals(I18n.format("test.missing"), "MISSING!test.missing");
	}

	@Test
	public void testFormatError() {
		assertEquals(I18n.format("test.format"), "FORMAT?test.format");
	}
}
