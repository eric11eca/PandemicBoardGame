import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.easymock.EasyMock;
import org.junit.Test;

public class TestCityDataParser {
	
	@Test (expected = FileNotFoundException.class)
	public void testParseFileNotFound() throws FileNotFoundException, IOException{
		CityDataParser parser = new CityDataParser();
		parser.parse(""); // list<list<String>>
	}
}
