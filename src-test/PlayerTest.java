import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PlayerTest {
	
	
	@Test 
	public void testNormalReceiveCityCard(){
		Player player = new Dispatcher();
		player.receiveCard(new CityCard("Beijing"));
		player.hand.contains("Beijing");
	}
}
