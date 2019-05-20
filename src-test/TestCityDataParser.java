import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import parse.CityDataParser;

public class TestCityDataParser {

	@Test(expected = RuntimeException.class)
	public void testParseFileNotFound() {
		CityDataParser parser = new CityDataParser();
		parser.parse("");
	}

	@Test
	public void testNormalParse() {
		CityDataParser parser = new CityDataParser();
		List<List<String>> cityData = parser.parse("testCityData");
		StringBuilder actual = new StringBuilder();
		String expect = "SanFrancisco,BLUE,884363,67,157,1;" + "Chicago,BLUE,2716000,200,121,5;"
				+ "Montreal,BLUE,1705000,306,119,2;" + "NewYork,BLUE,8623000,385,130,4;";
		for (List<String> city : cityData) {
			int dataBeforeAdjCities = 5;
			for (int i = 0; i < dataBeforeAdjCities; i++) {
				actual.append(city.get(i));
				actual.append(",");
			}
			actual.append(city.size() - dataBeforeAdjCities);
			actual.append(";");
		}
		assertEquals(actual.toString(), expect);
	}
}
