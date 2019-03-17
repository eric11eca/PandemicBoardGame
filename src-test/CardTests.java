import static org.junit.Assert.*;

import org.junit.Test;

public class CardTests {

	@Test
	public void test() {
			PCard card = new CityCard("Sydney");
			assertEquals("Sydney", card.getName());
			PCard card2 = new EventCard("Epidemic");
			assertEquals("Epidemic", card2.getName() );
	}

}
