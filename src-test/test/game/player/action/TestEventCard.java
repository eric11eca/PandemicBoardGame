package test.game.player.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.Deck;
import game.cards.event.CardEvent;
import game.event.Event;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionEventCard;
import mock.MockInteraction;

public class TestEventCard {
	private Player player;
	private MockInteraction interaction;
	private Card eventCard;
	private Deck discard;
	private boolean cbExecuted;

	@Before
	public void setup() {
		interaction = new MockInteraction().implementSelectPlayerFrom(this::selectThePlayer)
				.implementSelectCardsFrom(this::selectEventCard);
		cbExecuted = false;
		discard = new Deck();
		player = new PlayerImpl(null, null, discard, interaction);

	}

	@Test
	public void testEventCard() {
		Event event = EasyMock.mock(Event.class);
		event.executeEvent(interaction);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(event);
		eventCard = new CardEvent(event);
		player.receiveCard(eventCard);
		Action action = new ActionEventCard(interaction, Arrays.asList(player));
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);
		assertTrue(cbExecuted);
		EasyMock.verify(event);
	}

	@Test
	public void testCannotPerformNoEvent() {
		Action action = new ActionEventCard(interaction, Arrays.asList(player));
		assertFalse(action.canPerform());
	}

	private void selectThePlayer(List<Player> toSelect, Consumer<Player> callback) {
		assertTrue(toSelect.contains(player));
		callback.accept(player);
	}

	private void selectEventCard(int number, List<Card> toSelect, Consumer<List<Card>> callback) {
		assertTrue(toSelect.contains(eventCard));
		callback.accept(Arrays.asList(eventCard));
	}
}
