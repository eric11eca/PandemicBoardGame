package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionCharterFlight;
import test.util.TestCityBuilder;

public class TestCharterFlight {
	TestCityBuilder cityFactory = new TestCityBuilder();
	
	@Test
	public void testCharterFlight() {
		String chicago = "Chicago";
		cityFactory = cityFactory.name(chicago);
		City chicagoCity = cityFactory.build();
		
		String newyork = "NewYork";
		cityFactory = cityFactory.name(newyork);
		City newyorkCity = cityFactory.build();		
				
		Card chicagoCityCard = new CardCity(chicagoCity);
		
		Player medic = new PlayerImpl(0, chicagoCity, new Deck<>(), null);
		Set<City> citySet = new HashSet<>();
		citySet.add(chicagoCity);
		citySet.add(newyorkCity);
		medic.receiveCard(chicagoCityCard);
		
		Action charterFlight = new ActionCharterFlight(new CitySet(citySet), medic, null);
		charterFlight.perform(null);
		
		assertFalse(medic.handContains(chicagoCityCard));
		assertEquals(newyork, medic.getLocation().getName());
		assertTrue(medic.discardedContains(chicagoCityCard));
		//assertEquals(3, medic.action);
	}

}
