import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest {
	
	
	@Test 
	public void testNormalReceiveAndDiscardCityCard(){
		Player player = new Dispatcher();
		player.receiveCard(new CityCard("Beijing"));
		assertTrue(player.handContains("Beijing"));
		player.discardCard("Beijing");
		assertFalse(player.handContains("Beijing"));
	}
	
}
