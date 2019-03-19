import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testPlayerCreation() {
		Player player1=new Player(1);
		assertEquals(1,player1.getPlayerNumber());
	}
	
	@Test
	public void testPlayerHand(){
		Player player1 = new Player(1);
		//ArrayList<Card> player1Hand = new ArrayList<Card>();
		//player1.addHand(player1Hand);
		//assertEquals(player1Hand, player1.getHand());
	}

}
