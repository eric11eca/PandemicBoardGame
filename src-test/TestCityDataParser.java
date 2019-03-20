import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.junit.Test;

public class TestCityDataParser {
	
	@Test (expected = RuntimeException.class)
	public void testParseFileNotFound(){
		CityDataParser parser = new CityDataParser();
		parser.parse(""); // list<list<String>>
	}
	
	@Test 
	public void testNormalParse(){
		CityDataParser parser = new CityDataParser();
		List<List<String>> cityData = parser.parse("testCityData");
		StringBuilder actual = new StringBuilder();
		String expect = "Chicago,BLUE;Istanbul,BLACK;"
				+ "LosAngeles,GREEN;Mexico City,GREEN;"
				+ "Seoul,RED;";
		for(List<String> city : cityData){
			actual.append(city.get(0));
			actual.append(",");
			actual.append(city.get(1));
			actual.append(";");
		}
		assertEquals(actual.toString(),expect);
	}
}
