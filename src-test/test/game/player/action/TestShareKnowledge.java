package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionGiveKnowledge;
import game.player.action.ActionTakeKnowledge;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestShareKnowledge {
	Player giver;
	Player receiver;
	
	City newyorkCity;
	MockCityBuilder newyorkBuilder;
	Card newyorkCard;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction giverInteraction;
	MockInteraction receiverInteraction;

	Deck discard;
	List<Player> playerList;
	List<Card> cardList;

	@Before
	public void setup() {
		newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();
		newyorkCard = new CardCity(newyorkCity);
		
		cbExecuted = false;
		
		receiver = new PlayerImpl(null, newyorkCity, new Deck(), new MockInteraction());
		
		playerList = new ArrayList<>();
		playerList.add(receiver);
		cardList = new ArrayList<>();
		cardList.add(newyorkCard);
		
		giverInteraction = new MockInteraction();
		giverInteraction.implementSelectCardsFrom(this::selectCardsFrom);
		giverInteraction.implementSelectPlayerFrom((players, callback) -> {
			assertTrue(players.contains(receiver));
			callback.accept(receiver);
		});
		
		discard = new Deck();
		giver = new PlayerImpl(null, newyorkCity, discard, giverInteraction);
		giver.receiveCard(newyorkCard);		
	}

	@Test
	public void testGiveCard() {
		assertEquals(newyorkCity, giver.getLocation());
		assertEquals(newyorkCity, receiver.getLocation());
		
		Action action = new ActionGiveKnowledge(giver, giverInteraction, playerList);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
	}
	
	@Test
	public void testTakeCard() {
		receiverInteraction = new MockInteraction();
		receiver = new PlayerImpl(null, newyorkCity, new Deck(), receiverInteraction);
		receiverInteraction.implementSelectPlayerFrom((players, callback) -> {
			assertTrue(players.contains(giver));
			callback.accept(giver);
		});
		receiverInteraction.implementSelectCardsFrom(this::selectCardsFrom);
		
		playerList = new ArrayList<>();
		playerList.add(giver);
		
		assertEquals(newyorkCity, giver.getLocation());
		assertEquals(newyorkCity, receiver.getLocation());
		
		Action action = new ActionTakeKnowledge(receiver, receiverInteraction, playerList);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
	}
	
	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(newyorkCard));
		callback.accept(cardList);
	}
}
