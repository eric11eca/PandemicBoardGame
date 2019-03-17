import static org.junit.Assert.*;

import org.junit.Test;

public class CardTests {

	@Test
	public void test() {
			PCard card = new CityCard("Sydney");
			assertEquals("Sydney", card.getName());
			card.function();
			PCard card2 = new EventCard("Epidemic");
			assertEquals("Epidemic", card2.getName() );
			card2.function();
			PCard card3 = new EventCard("One Quiet Night");
			card3.function();
			assertEquals("One Quiet Night", card.getName());
	}

}
