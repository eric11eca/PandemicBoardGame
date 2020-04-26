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
import game.cards.event.CardEventReused;
import game.event.Event;
import game.player.action.Action;
import game.player.action.ActionSkillContingencyPlanner;
import game.player.special.ContingencyPlanner;
import mock.MockInteraction;

public class TestSkillContingencyPlanner {
	private ContingencyPlanner player;

	private boolean cbExecuted;
	private MockInteraction interaction;

	private Card eventCard;

	private Deck discard;

	@Before
	public void setup() {

		player = EasyMock.mock(ContingencyPlanner.class);

		cbExecuted = false;

		interaction = new MockInteraction().implementSelectCardsFrom(this::selectCardsFrom);
		eventCard = new CardEvent(EasyMock.mock(Event.class));

		discard = new Deck();
		discard.putOnTop(eventCard);
	}

	@Test
	public void testReuseEventCard() {
		EasyMock.expect(player.hasEventCardOnRole()).andReturn(false);
		player.keepEventCardOnRole(new CardEventReused(eventCard));
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(player);

		Action action = new ActionSkillContingencyPlanner(player, interaction, discard);
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		interaction.verifySelectCardsCalled(1);

		assertTrue(cbExecuted);
		EasyMock.verify(player);
	}

	@Test
	public void testCannotPerformNoDiscard() {
		Action action = new ActionSkillContingencyPlanner(player, interaction, new Deck());
		assertFalse(action.canPerform());
	}

	@Test
	public void testCannotPerformAlreadyHasCard() {
		EasyMock.expect(player.hasEventCardOnRole()).andReturn(true);
		EasyMock.replay(player);

		Action action = new ActionSkillContingencyPlanner(player, interaction, discard);
		assertFalse(action.canPerform());
		EasyMock.verify(player);
	}

	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(eventCard));
		callback.accept(Arrays.asList(eventCard));
	}

}
