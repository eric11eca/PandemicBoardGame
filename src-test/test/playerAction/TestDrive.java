package test.playerAction;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.cards.Deck;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerInteraction;
import game.player.action.Action;
import game.player.action.ActionDrive;
import test.MockCityBuilder;

public class TestDrive {
	Player player;
	City location, neighborCity, notNeighborCity;

	MockCityBuilder cityFactory = new MockCityBuilder();

	@Before
	public void setup() {
		cityFactory = cityFactory.name("Atlanta"); 
		neighborCity = cityFactory.build();
		cityFactory = cityFactory.name("Chicago"); 
		cityFactory.neighborSet().add(neighborCity);
		location = cityFactory.build();
		cityFactory = cityFactory.name("Shanghai"); 
		notNeighborCity = cityFactory.build();
		player = new PlayerImpl(0, location, new Deck<>(), null);
	}

	@Test
	public void testNeighborDrive() {
		Action drive = EasyMock.mock(ActionDrive.class);
		PlayerInteraction interaction = EasyMock.mock(PlayerInteraction.class);
		Set<City> citySet = new HashSet<>();
		citySet.add(location);
		
		drive.perform(null);
		EasyMock.expectLastCall().andAnswer(()->{
			interaction.selectCityFrom(citySet, null);
			return null;
		});
		
		interaction.selectCityFrom(citySet, null);
		EasyMock.expectLastCall().andAnswer(()->{
			performDriveAction(neighborCity, null);
			return null;
		});
		
		EasyMock.replay(drive, interaction);
		drive.perform(null);
		assertEquals(player.getLocation().getName(), "Atlanta");
		EasyMock.verify(drive, interaction);
	}
	
	private void performDriveAction(City destination, Runnable completionCallback) {
		player.setLocation(destination);
		completionCallback.run();
	}
}
