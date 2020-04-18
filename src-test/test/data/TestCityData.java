package test.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import data.CityData;
import game.GameColor;

public class TestCityData {

	@Test
	public void testName() {
		CityData data = new CityData("name", GameColor.BLACK, 12345, false);
		assertEquals(data.getCityName(), "name");
	}

	@Test
	public void testStart() {
		CityData nostart = new CityData("name", GameColor.BLACK, 12345, false);
		assertFalse(nostart.isStart());
		CityData start = new CityData("name", GameColor.BLACK, 12345, true);
		assertTrue(start.isStart());
	}

	@Test
	public void testColor() {
		CityData data = new CityData("name", GameColor.BLACK, 12345, false);
		assertEquals(data.getColor(), GameColor.BLACK);
	}

	@Test
	public void testPopulation() {
		CityData data = new CityData("name", GameColor.BLACK, 12345, false);
		assertEquals(data.getPopulation(), 12345);
	}

	@Test
	public void testEquals() {
		CityData data1 = new CityData("name", GameColor.BLACK, 12345, false);
		CityData data2 = new CityData("name2", GameColor.BLACK, 12345, false);
		CityData data3 = new CityData("name", GameColor.BLACK, 12345, false);
		assertTrue(data1.equals(data1));
		assertTrue(data1.equals(data3));
		assertFalse(data1.equals(data2));
		assertFalse(data3.equals(data2));
		assertFalse(data1.equals(null));
	}

}
