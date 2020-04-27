package test.game.city;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import data.CityData;
import game.GameColor;
import game.GameState;
import game.city.City;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class TestCity {

	@Test
	public void testDataDelegate() {
		CityData delegate = EasyMock.mock(CityData.class);
		City city = new City(delegate, null, null);
		EasyMock.expect(delegate.getCityName()).andReturn("testCityName");
		EasyMock.expect(delegate.getColor()).andReturn(GameColor.BLACK);
		EasyMock.expect(delegate.getPopulation()).andReturn(1);
		EasyMock.expect(delegate.isStart()).andReturn(true);
		EasyMock.replay(delegate);
		assertEquals("testCityName", city.getName());
		assertEquals(GameColor.BLACK, city.getColor());
		assertEquals(1, city.getPopulation());
		assertTrue(city.isStartingCity());
		assertEquals(Objects.hash(delegate), city.hashCode());
		EasyMock.verify(delegate);
	}

	@Test
	public void testResearchStation() {
		City city = new City(null, null, null);
		assertFalse(city.hasResearchStation());
		city.buildResearchStation();
		assertTrue(city.hasResearchStation());
		city.removeResearchStation();
		assertFalse(city.hasResearchStation());
	}

	@Test
	public void testDiseaseBasicDelegate() {
		CubeData delegate = EasyMock.mock(CubeData.class);
		CityData data = EasyMock.mock(CityData.class);
		delegate.setDiseaseCubeCount(GameColor.BLACK, 12);
		EasyMock.expectLastCall().andVoid();
		EasyMock.expect(delegate.getDiseaseCubeCount(GameColor.BLACK)).andReturn(12);
		EasyMock.expect(delegate.getExistingDiseases()).andReturn(EnumSet.of(GameColor.BLACK));
		EasyMock.expect(data.getColor()).andReturn(GameColor.BLACK);
		EasyMock.replay(delegate, data);

		City city = new City(data, delegate, null);
		city.initializeDisease(12);
		assertEquals(12, city.getDiseaseCubeCount(GameColor.BLACK));
		Set<GameColor> existingDisease = city.getExistingDiseases();
		assertTrue(existingDisease.contains(GameColor.BLACK));
		assertEquals(1, existingDisease.size());
		EasyMock.verify(delegate, data);
	}

	@Test
	public void testHasDiseaseDelegate() {
		CubeData delegate = EasyMock.mock(CubeData.class);
		EasyMock.expect(delegate.getExistingDiseases()).andReturn(EnumSet.of(GameColor.BLACK));
		EasyMock.replay(delegate);
		City city = new City(null, delegate, null);

		assertTrue(city.hasDisease());
		EasyMock.verify(delegate);

		delegate = EasyMock.mock(CubeData.class);
		EasyMock.expect(delegate.getExistingDiseases()).andReturn(EnumSet.noneOf(GameColor.class));
		EasyMock.replay(delegate);
		city = new City(null, delegate, null);

		assertFalse(city.hasDisease());
		EasyMock.verify(delegate);
	}

	@Test
	public void testTreatDisease() {
		CubeData delegate = EasyMock.mock(CubeData.class);
		delegate.removeDiseaseCube(GameColor.BLACK);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(delegate);

		City city = new City(null, delegate, null);
		city.treatDisease(GameColor.BLACK);
		EasyMock.verify(delegate);
	}

	@Test
	public void testEradicateDisease() {
		CubeData delegate = EasyMock.mock(CubeData.class);
		delegate.removeAllDiseaseCube(GameColor.BLACK);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(delegate);

		City city = new City(null, delegate, null);
		city.eradicateDisease(GameColor.BLACK);
		EasyMock.verify(delegate);
	}

	@Test
	public void testNeighbor() {
		City neighbor = EasyMock.mock(City.class);
		City Notneighbor = EasyMock.mock(City.class);
		Set<City> neighbors = new HashSet<>();
		neighbors.add(neighbor);

		City city = new City(null, null, neighbors);
		assertTrue(city.isNeighboring(neighbor));
		assertFalse(city.isNeighboring(Notneighbor));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		City city = new City(null, null, null);
		assertFalse(city.equals(null));
		assertFalse(city.equals("Not a city"));

		CityData data1 = EasyMock.mock(CityData.class);
		CityData data2 = EasyMock.mock(CityData.class);

		assertFalse(new City(data1, null, null).equals(new City(data2, null, null)));
		assertTrue(new City(data1, null, null).equals(new City(data1, null, null)));
	}

	@Test
	public void testInfectNoOutbreak() {
		CubeData disease = new CubeDataImpl();
		CityData data = new CityData("name", GameColor.RED, 12345, false);
		City city = new City(data, disease, null);
		GameState game = EasyMock.mock(GameState.class);
		game.increaseOutbreakLevel(0);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(game);
		city.infect(game, c -> false);

		EasyMock.verify(game);
		assertEquals(1, disease.getDiseaseCubeCount(GameColor.RED));

	}

//	/**
//	 * Performs epidemic infection on this city. Outbreaks will be automatically
//	 * triggered and recorded by the {@link GameState}.
//	 * 
//	 * @param game          the GameState object
//	 * @param isQuarantined The quarantine tester.
//	 */
//	public void epidemicInfect(GameState game, Predicate<City> isQuarantined) {
//		int outbreakCount = epidemicInfectAndGetOutbreakCount(isQuarantined);
//		game.increaseOutbreakLevel(outbreakCount);
//	}
//
//
//
//	// return how many outbreaks happened
//	private int epidemicInfectAndGetOutbreakCount(Predicate<City> isQuarantined) {
//		if (isQuarantined.test(this))
//			return 0;
//
//		boolean willOutbreak = disease.getDiseaseCubeCount(getColor()) > 0;
//		int addCubeCount = 3 - disease.getDiseaseCubeCount(getColor());
//		disease.addDiseaseCube(getColor(), addCubeCount);
//
//		if (willOutbreak) {
//			return this.outbreak(getColor(), isQuarantined, new HashSet<>(), 0);
//		} else {
//			return 0;
//		}
//	}
//
//	// return how many outbreaks happened
//	private int infectAndGetOutbreakCount(GameColor diseaseColor, Predicate<City> isQuarantined) {
//		return infectAndGetOutbreakCountHelper(diseaseColor, isQuarantined, new HashSet<>(), 0);
//	}
//
//	private int infectAndGetOutbreakCountHelper(GameColor diseaseColor, Predicate<City> isQuarantined,
//			Set<City> inOutBreak, int outbreakCount) {
//		if (isQuarantined.test(this))
//			return outbreakCount;
//
//		boolean willOutbreak = disease.getDiseaseCubeCount(diseaseColor) == 3;
//
//		if (willOutbreak) {
//			return this.outbreak(diseaseColor, isQuarantined, inOutBreak, outbreakCount + 1);
//		} else {
//			disease.addDiseaseCube(diseaseColor);
//		}
//		return outbreakCount;
//	}
//
//	private int outbreak(GameColor outbreakColor, Predicate<City> isQuarantined, Set<City> inOutBreak,
//			int outbreakCount) {
//		inOutBreak.add(this);
//		for (City city : neighbors) {
//			if (!inOutBreak.contains(city)) {
//				outbreakCount = city.infectAndGetOutbreakCountHelper(outbreakColor, isQuarantined, inOutBreak,
//						outbreakCount);
//			}
//		}
//		return outbreakCount;
//	}

}
