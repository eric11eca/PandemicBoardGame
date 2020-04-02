import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import data.CityData;
<<<<<<< HEAD
import data.GameColor;
import game.city.City;
=======
import game.City;
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
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
<<<<<<< HEAD
			board.cities.put(citynames[i],
					new City(new CityData(citynames[i], GameColor.compatibility_getByName(cityColors[i]), 10)));
=======
			//cityColors[i]
			CityData data = new CityData(citynames[i], null, 0);
			board.cities.put(citynames[i], new City(data, 0, 0));
>>>>>>> 363c96c06ae2c3172da91a173d6066e085d666a4
		}
	}

	@Test
	public void testGenerateLostOfCityWithColor() {
		String expected = "city1 [BLUE], city2 [BLUE], city3 [RED], city4 [RED], city5 [BLACK], city6 [BLACK], "
				+ "city7 [YELLOW], city8 [YELLOW], city9 [RED], city10 [RED]";
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
	
	@Test
	public void testWithInvalidCity() {
		String[] cityname = {"Cancel"};
		String[] cityWithColor = generator.concatColor(cityname, board.cities);
		String expected = "Cancel";
		String actual = cityWithColor[0];
		assertEquals(expected, actual);
	}
}
