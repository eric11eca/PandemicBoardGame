import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest {
	/*@Test
	public void testPlayerHand(){
		Player player1 = new Player(1);
		//ArrayList<Card> player1Hand = new ArrayList<Card>();
		//player1.addHand(player1Hand);
		//assertEquals(player1Hand, player1.getHand());
	}*/
	
	@Test
	public void testHandCapacity() {
		Player p1 = new Player(4);
		assertEquals(2, p1.getHandCapacity());
		
		Player p2 = new Player(3);
		assertEquals(3, p2.getHandCapacity());
		
		Player p3 = new Player(2);
		assertEquals(4, p3.getHandCapacity());
	}

}
