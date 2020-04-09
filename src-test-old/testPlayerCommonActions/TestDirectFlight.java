package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
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
import game.player.action.Action;
import game.player.action.ActionDirectFlight;
import test.util.TestCityBuilder;

public class TestDirectFlight {
	Player player;
	Card eventCard;
	Card newyorkCityCard, chicagoCityCard;
	City chicagoCity, newyorkCity;
	TestCityBuilder cityFactory = new TestCityBuilder();

	@Before
	public void setup() {
		String chicago = "Chicago";
		cityFactory = cityFactory.name(chicago);
		chicagoCity = cityFactory.build();
		
		String newyork = "New York";
		cityFactory = cityFactory.name(newyork);
		newyorkCity = cityFactory.build();
		System.out.println(newyorkCity.getName());
		
		player = new PlayerImpl(0, chicagoCity, new Deck<>(), null);
		player.setLocation(chicagoCity);
		
		Event airlift = new EventAirlift(null, null);
		
		newyorkCityCard = new CardCity(newyorkCity);
		chicagoCityCard = new CardCity(chicagoCity);
		eventCard = new CardEvent(airlift); 
		
		List<Card> cards = Arrays.asList(newyorkCityCard, chicagoCityCard, eventCard);
		player.receiveCard(cards);
	}

	@Test
	public void testSuccessDirectFlight() {		
		Action directFlight = new ActionDirectFlight(player, null);
		System.out.println(player.getLocation().getName());
		
		assertEquals("New York", player.getLocation().getName());
		directFlight.perform(null);
		
		System.out.println(player.getLocation().getName());
		assertEquals(player.getCardNumberInHand(), 2);
		assertFalse(player.handContains(chicagoCityCard));
		assertEquals("New York", player.getLocation().getName());
		assertTrue(player.discardedContains(chicagoCityCard));
		
		assertEquals(1,0);
	}

}
