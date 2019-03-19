import static org.junit.Assert.*;

import org.junit.Test;

public class CardTests {

	@Test
	public void test() {
			PlayerCard card = new CityCard("Sydney");
			assertEquals("Sydney", card.getName());
			card.function();
			PlayerCard card2 = new EventCard("Epidemic");
			assertEquals("Epidemic", card2.getName() );
			card2.function();
			PlayerCard card3 = new EventCard("One Quiet Night");
			card3.function();
			assertEquals("One Quiet Night", card.getName());
	}

}
