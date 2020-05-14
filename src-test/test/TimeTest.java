package test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import initialize.InitializationFacade;

public class TimeTest {
	@Test
	public void testSetupTime() throws IOException {
		final long ALLOWED = 500;
		long start = System.currentTimeMillis();
		InitializationFacade initialization = new InitializationFacade(4, 6);
		initialization.createGUI();
		long end = System.currentTimeMillis();
		assertTrue(end - start < ALLOWED);
	}
}
