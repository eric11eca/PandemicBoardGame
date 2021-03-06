package test.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.CityLoader;
import game.GameColor;
import game.city.City;
import game.disease.CubeData;
import mock.MockCityBuilder;
import mock.MockGameProperty;
import mock.ReflectionAccess;
import render.RenderCity;

public class TestCityLoader {

	ReflectionAccess access = new ReflectionAccess();
	MockGameProperty property = new MockGameProperty();

	@Before
	@After
	public void resetProperty() {
		property.resetAndEject();
	}

	@Test(expected = IOException.class)
	public void testParseFileNotFound() throws IOException {
		property.put("DATA_PATH", "testdata/");
		property.put("CITY_DATA", "dne");
		property.inject();
		CityLoader parser = new CityLoader(new HashMap<>(), new HashMap<>()) {
			@Override
			protected CubeData createCubeData() {
				return null;
			}

		};

		parser.loadCities();
	}

	@Test
	public void testNormalParse() throws IOException {
		property.put("DATA_PATH", "testdata/");
		property.put("CITY_DATA", "test_citydata.dat");
		property.inject();

		Map<City, RenderCity> cities = new HashMap<>();
		CityLoader parser = new CityLoader(new HashMap<>(), cities) {
			@Override
			protected CubeData createCubeData() {
				return null;
			}

			@Override
			protected String formatCityName(String rawName) {
				return rawName;
			}

		};
		parser.loadCities();
		TreeMap<City, RenderCity> sorted = new TreeMap<>((c1, c2) -> c1.getName().compareTo(c2.getName()));
		cities.forEach(sorted::put);

		City SF = new MockCityBuilder().name("SanFrancisco").color(GameColor.BLUE).population(884363).build();
		City chicago = new MockCityBuilder().name("Chicago").color(GameColor.BLUE).population(2716000).build();
		City montreal = new MockCityBuilder().name("Montreal").color(GameColor.BLUE).population(1705000).build();
		City NY = new MockCityBuilder().name("NewYork").color(GameColor.BLUE).population(8623000).build();

		Iterator<Entry<City, RenderCity>> iterator = sorted.entrySet().iterator();
		assertTrue(iterator.hasNext());
		Entry<City, RenderCity> next = iterator.next();
		City expectChicago = next.getKey();
		assertEquals(expectChicago, chicago);
		assertTrue(expectChicago.isNeighboring(SF));
		assertTrue(expectChicago.isNeighboring(montreal));
		RenderCity renderChicago = next.getValue();
		assertEquals(200, access.accessField(RenderCity.class, renderChicago, "x", Integer.class).intValue());
		assertEquals(121, access.accessField(RenderCity.class, renderChicago, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectMontreal = next.getKey();
		assertEquals(expectMontreal, montreal);
		assertTrue(expectMontreal.isNeighboring(NY));
		assertTrue(expectMontreal.isNeighboring(chicago));
		RenderCity renderMontreal = next.getValue();
		assertEquals(306, access.accessField(RenderCity.class, renderMontreal, "x", Integer.class).intValue());
		assertEquals(119, access.accessField(RenderCity.class, renderMontreal, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectNY = next.getKey();
		assertEquals(expectNY, NY);
		assertTrue(expectNY.isNeighboring(montreal));
		RenderCity renderNY = next.getValue();
		assertEquals(385, access.accessField(RenderCity.class, renderNY, "x", Integer.class).intValue());
		assertEquals(130, access.accessField(RenderCity.class, renderNY, "y", Integer.class).intValue());

		assertTrue(iterator.hasNext());
		next = iterator.next();
		City expectSF = next.getKey();
		assertEquals(expectSF, SF);
		assertTrue(expectSF.isNeighboring(chicago));
		RenderCity renderSF = next.getValue();
		assertEquals(67, access.accessField(RenderCity.class, renderSF, "x", Integer.class).intValue());
		assertEquals(157, access.accessField(RenderCity.class, renderSF, "y", Integer.class).intValue());

		assertFalse(iterator.hasNext());
	}
}
