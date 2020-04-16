package test.playerAction;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.cards.event.CardEvent;
import game.city.City;
import game.city.CitySet;
import game.event.Event;
import game.event.EventAirlift;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionEventCard;
import test.MockCityBuilder;
import test.MockInteraction;

public class TestUseEventCard {
	Player player;
	City chicagoCity, newyorkCity;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;
	
	Card newyorkCard;
	Card evetCard;
	List<Card> cardList;

	@Before
	public void setup() {
		cardList = new ArrayList<>();
		
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();
		
		newyorkCard = new CardCity(newyorkCity);

		cbExecuted = false;
		interaction = new MockInteraction();		
		interaction.implementSelectCardsFrom(this::selectCardsFrom);
		
		player = new PlayerImpl(0, newyorkCity, new Deck(), interaction);
		player.receiveCard(newyorkCard);
		
		interaction.implementSelectPlayerFrom((players, callback)->{
			//assertTrue(players.contains(player));
			callback.accept(player);
		});
		
		interaction.implementSelectCityFrom((citiesToSelectFrom, callback) -> {
			//assertTrue(citiesToSelectFrom.contains(newyorkCity));
			callback.accept(newyorkCity);
		});
	}

	@Test
	public void testUseEventCard() {
		List<Player> playerList = new ArrayList<>();
		playerList.add(player);
		
		Set<City> citySet = new HashSet<>();
		citySet.add(newyorkCity);
		
		Event airlift = new EventAirlift(playerList, new CitySet(citySet));
		evetCard = new CardEvent(airlift);
		cardList.add(evetCard);
		player.receiveCard(evetCard);
		
		Action action = new ActionEventCard(interaction, playerList);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
	}
	
	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		//assertTrue(cards.contains(evetCard));
		callback.accept(cardList);
	} 
}
