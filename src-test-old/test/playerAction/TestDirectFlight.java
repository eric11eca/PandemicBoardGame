package test.playerAction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.cards.event.CardEvent;
import game.city.City;
import game.event.Event;
import game.event.EventAirlift;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerInteraction;
import game.player.action.Action;
import game.player.action.ActionDirectFlight;
import mock.MockCityBuilder;

public class TestDirectFlight {
	Player player;
	Card eventCard;
	Card newyorkCityCard, chicagoCityCard;
	City chicagoCity, newyorkCity;
	MockCityBuilder cityFactory = new MockCityBuilder();

	@Before
	public void setup() {
		String chicago = "Chicago";
		cityFactory = cityFactory.name(chicago);
		chicagoCity = cityFactory.build();
		
		String newyork = "New York";
		cityFactory = cityFactory.name(newyork);
		newyorkCity = cityFactory.build();
		
		player = new PlayerImpl(0, newyorkCity, new Deck<>(), null);
		
		Event airlift = new EventAirlift(null, null);
		
		newyorkCityCard = new CardCity(newyorkCity);
		chicagoCityCard = new CardCity(chicagoCity);
		eventCard = new CardEvent(airlift); 
		
		List<Card> cards = Arrays.asList(newyorkCityCard, chicagoCityCard, eventCard);
		player.receiveCard(cards);
	}

	@Test
	public void testSuccessDirectFlight() {		
		Action directFlight = EasyMock.mock(ActionDirectFlight.class);
		PlayerInteraction interaction = EasyMock.mock(PlayerInteraction.class);
		
		List<Card> cards = Arrays.asList(newyorkCityCard, chicagoCityCard, eventCard);
		
		directFlight.perform(null);
		EasyMock.expectLastCall().andAnswer(()->{
			interaction.selectOneCardFrom(cards, null);
			return null;
		});
		
		interaction.selectOneCardFrom(cards, null);
		EasyMock.expectLastCall().andAnswer(()->{
			performDirectFlightAction(newyorkCityCard, null);
			return null;
		});
		
		assertEquals("Chicago", player.getLocation().getName());
		directFlight.perform(null);
		
		assertEquals("New York", player.getLocation().getName());
	
		
	}
	
	private void performDirectFlightAction(Card usingCard, Runnable completionCallback) {
		City city = usingCard.getCity().get();
		assert !city.equals(player.getLocation());
		player.setLocation(city);
		player.discardCard(usingCard);
		completionCallback.run();
	}

}
