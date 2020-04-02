import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Test;

import data.GameColor;
import game.city.City;
import helpers.TestAccess;
import helpers.TestCityFactory;
import parse.CityDataParser;
import render.RenderCity;

public class TestCityDataParser {

	TestCityFactory cityFactory = new TestCityFactory();
	TestAccess access = new TestAccess();

	@Test(expected = IOException.class)
	public void testParseFileNotFound() throws IOException {
		CityDataParser parser = new CityDataParser();
		parser.loadCities(new File(""));
	}

	@Test
	public void testNormalParse() throws IOException {
		CityDataParser parser = new CityDataParser();
		Map<City, RenderCity> cities = parser.loadCities(new File("testCityData"));
		TreeMap<City, RenderCity> sorted = new TreeMap<>((c1, c2) -> c1.getName().compareTo(c2.getName()));
		cities.forEach(sorted::put);

		City SF = cityFactory.makeFakeCity("SanFrancisco", GameColor.BLUE, 884363);
		City chicago = cityFactory.makeFakeCity("Chicago", GameColor.BLUE, 2716000);
		City montreal = cityFactory.makeFakeCity("Montreal", GameColor.BLUE, 1705000);
		City NY = cityFactory.makeFakeCity("NewYork", GameColor.BLUE, 8623000);

		Iterator<Entry<City, RenderCity>> iterator = sorted.entrySet().iterator();
		assertTrue(iterator.hasNext());
		Entry<City, RenderCity> next = iterator.next();
		City expectChicago = next.getKey();
		assertEquals(expectChicago, chicago);
		assertTrue(expectChicago.isNeighbor(SF));
		assertTrue(expectChicago.isNeighbor(montreal));
		RenderCity renderChicago = next.getValue();
		assertEquals(200, access.accessField(RenderCity.class, renderChicago, "x", Integer.class).intValue());
		assertEquals(121, access.accessField(RenderCity.class, renderChicago, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectMontreal = next.getKey();
		assertEquals(expectMontreal, montreal);
		assertTrue(expectMontreal.isNeighbor(NY));
		assertTrue(expectMontreal.isNeighbor(chicago));
		RenderCity renderMontreal = next.getValue();
		assertEquals(306, access.accessField(RenderCity.class, renderMontreal, "x", Integer.class).intValue());
		assertEquals(119, access.accessField(RenderCity.class, renderMontreal, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectNY = next.getKey();
		assertEquals(expectNY, NY);
		assertTrue(expectNY.isNeighbor(montreal));
		RenderCity renderNY = next.getValue();
		assertEquals(385, access.accessField(RenderCity.class, renderNY, "x", Integer.class).intValue());
		assertEquals(130, access.accessField(RenderCity.class, renderNY, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectSF = next.getKey();
		assertEquals(expectSF, SF);
		assertTrue(expectSF.isNeighbor(chicago));
		RenderCity renderSF = next.getValue();
		assertEquals(67, access.accessField(RenderCity.class, renderSF, "x", Integer.class).intValue());
		assertEquals(157, access.accessField(RenderCity.class, renderSF, "y", Integer.class).intValue());

		assertFalse(iterator.hasNext());
	}
}
