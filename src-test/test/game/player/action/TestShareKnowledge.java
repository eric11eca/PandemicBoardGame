package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionGiveKnowledge;
import game.player.action.ActionTakeKnowledge;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestShareKnowledge {
	private Player giver;
	private Player receiver;

	private City newyorkCity;
	private Card newyorkCard;

	private boolean cbExecuted;
	private MockInteraction giverInteraction;
	private MockInteraction receiverInteraction;

	private List<Player> playerList;

	@Before
	public void setup() {
		newyorkCity = new MockCityBuilder().name("NewYork").build();
		newyorkCard = new CardCity(newyorkCity);

		cbExecuted = false;

		giverInteraction = new MockInteraction().implementSelectCardsFrom(this::selectNewYork)
				.implementSelectPlayerFrom(this::selectReceiver);
		receiverInteraction = new MockInteraction().implementSelectPlayerFrom(this::selectGiver)
				.implementSelectCardsFrom(this::selectNewYork);

		giver = new PlayerImpl(null, newyorkCity, null, giverInteraction);
		giver.receiveCard(newyorkCard);
		receiver = new PlayerImpl(null, newyorkCity, null, receiverInteraction);

		playerList = Arrays.asList(receiver, giver);
	}

	@Test
	public void testGiveCard() {
		assertEquals(newyorkCity, giver.getLocation());
		assertEquals(newyorkCity, receiver.getLocation());
		assertEquals(1, giver.getFilteredHand(c -> true).size());
		assertEquals(0, receiver.getFilteredHand(c -> true).size());
		Action action = new ActionGiveKnowledge(giver, giverInteraction, playerList);
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		giverInteraction.verifySelectPlayerCalled(1);
		giverInteraction.verifySelectCardsCalled(1);

		assertTrue(cbExecuted);
		assertEquals(0, giver.getFilteredHand(c -> true).size());
		assertEquals(1, receiver.getFilteredHand(c -> true).size());
	}

	@Test
	public void testTakeCard() {

		assertEquals(newyorkCity, giver.getLocation());
		assertEquals(newyorkCity, receiver.getLocation());
		assertEquals(1, giver.getFilteredHand(c -> true).size());
		assertEquals(0, receiver.getFilteredHand(c -> true).size());
		Action action = new ActionTakeKnowledge(receiver, receiverInteraction, playerList);
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		receiverInteraction.verifySelectPlayerCalled(1);
		receiverInteraction.verifySelectCardsCalled(1);

		assertTrue(cbExecuted);
		assertEquals(0, giver.getFilteredHand(c -> true).size());
		assertEquals(1, receiver.getFilteredHand(c -> true).size());
	}

	@Test
	public void testCannotPerform() {
		giver.setLocation(new MockCityBuilder().build());
		Action action = new ActionTakeKnowledge(receiver, receiverInteraction, playerList);
		assertFalse(action.canPerform());
		Action action2 = new ActionGiveKnowledge(giver, receiverInteraction, playerList);
		assertFalse(action2.canPerform());
	}

	private void selectNewYork(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(newyorkCard));
		callback.accept(Arrays.asList(newyorkCard));
	}

	private void selectReceiver(List<Player> players, Consumer<Player> callback) {
		assertTrue(players.contains(receiver));
		callback.accept(receiver);
	}

	private void selectGiver(List<Player> players, Consumer<Player> callback) {
		assertTrue(players.contains(giver));
		callback.accept(giver);
	}
}
