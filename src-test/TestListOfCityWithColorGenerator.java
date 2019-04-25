import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Parse.ListOfCityWithColorGenerator;

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
			board.cities.put(citynames[i], new City(citynames[i], cityColors[i]));
		}
	}

	@Test
	public void testGenerateLostOfCityWithColor() {
		String expected = "[BLUE] city1, [BLUE] city2, [RED] city3, [RED] city4, [BLACK] city5, [BLACK] city6, "
				+ "[YELLOW] city7, [YELLOW] city8, [RED] city9, [RED] city10";
		String[] cityWithColor = generator.concatColor(citynames, board.cities);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < citynames.length - 1; i++) {
			sb.append(cityWithColor[i]);
			sb.append(", ");
		}
		sb.append(cityWithColor[citynames.length - 1]);
		String actual = sb.toString();
		assertEquals(expected, actual);
	}
}
