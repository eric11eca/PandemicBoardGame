package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerInteraction;
import game.player.action.Action;
import game.player.action.ActionCharterFlight;
import mock.MockCityBuilder;

public class TestCharterFlight {
	Player medic;
	MockCityBuilder cityFactory = new MockCityBuilder();
	
	@Test
	public void testCharterFlight() {
		String chicago = "Chicago";
		cityFactory = cityFactory.name(chicago);
		City chicagoCity = cityFactory.build();
		
		String newyork = "NewYork";
		cityFactory = cityFactory.name(newyork);
		City newyorkCity = cityFactory.build();		
				
		Card chicagoCityCard = new CardCity(chicagoCity);
		
		medic = new PlayerImpl(0, chicagoCity, new Deck<>(), null);
		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(newyorkCity);
		medic.receiveCard(chicagoCityCard);
		
		Action charterFlight = EasyMock.mock(ActionCharterFlight.class);
		PlayerInteraction interaction = EasyMock.mock(PlayerInteraction.class);
		
		charterFlight.perform(null);
		EasyMock.expectLastCall().andAnswer(()->{
			interaction.selectCityFrom(citySet, null);
			return null;
		});
		
		interaction.selectCityFrom(citySet, null);
		EasyMock.expectLastCall().andAnswer(()->{
			performCharterFlightAction(chicagoCityCard, newyorkCity, null);
			return null;
		});
		
		EasyMock.replay(charterFlight, interaction);
		charterFlight.perform(null);
		assertFalse(medic.handContains(chicagoCityCard));
		assertEquals(newyork, medic.getLocation().getName());
		assertTrue(medic.discardedContains(chicagoCityCard));
		EasyMock.verify(charterFlight, interaction);
	}
	
	private void performCharterFlightAction(Card usingCard, City flyTo, Runnable completionCallback) {
		City city = usingCard.getCity().orElseThrow(RuntimeException::new);
		assert city.equals(medic.getLocation());
		medic.setLocation(flyTo);
		medic.discardCard(usingCard);
		completionCallback.run();
	}

}
