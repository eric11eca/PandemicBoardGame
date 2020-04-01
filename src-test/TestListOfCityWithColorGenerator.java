import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.CityData;
import data.CityOLD;
import data.GameColor;
import parse.ListOfCityWithColorGenerator;

public class TestListOfCityWithColorGenerator {
	Board board;
	ListOfCityWithColorGenerator generator;
	String[] citynames = { "city1", "city2", "city3", "city4", "city5", "city6", "city7", "city8", "city9", "city10" };
	String[] cityColors = { "BLUE", "BLUE", "RED", "RED", "BLACK", "BLACK", "YELLOW", "YELLOW", "RED", "RED" };

	@Before
	public void setup() {
		board = new Board();
		generator = new ListOfCityWithColorGenerator();
		for (int i = 0; i < citynames.length; i++) {
			board.cities.put(citynames[i], new CityOLD(
					new CityData(citynames[i], GameColor.compatibility_getByName(cityColors[i]), 10), 0, 0));
		}
	}

	@Test
	public void testGenerateLostOfCityWithColor() {
		String expected = "city1 [BLUE], city2 [BLUE], city3 [RED], city4 [RED], city5 [BLACK], city6 [BLACK], "
				+ "city7 [YELLOW], city8 [YELLOW], city9 [RED], city10 [RED]";
		String[] cityWithColor = generator.concatColor(citynames, board.cities);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < citynames.length - 1; i++) {
			sb.append(cityWithColor[i]);
			sb.append(", ");
		}
		sb.append(cityWithColor[citynames.length - 1]);
		String actual = sb.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testWithInvalidCity() {
		String[] cityname = { "Cancel" };
		String[] cityWithColor = generator.concatColor(cityname, board.cities);
		String expected = "Cancel";
		String actual = cityWithColor[0];
		assertEquals(expected, actual);
	}
}
