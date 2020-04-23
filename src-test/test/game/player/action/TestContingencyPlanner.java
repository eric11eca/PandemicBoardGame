package test.game.player.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.Deck;
import game.cards.event.CardEvent;
import game.city.City;
import game.event.Event;
import game.event.EventGovernmentGrant;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionSkillContingencyPlanner;
import game.player.special.ContingencyPlanner;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestContingencyPlanner {
	ContingencyPlanner planner;
	City newyorkCity;
	
	Player player;
	boolean cbExecuted;
	MockInteraction interaction;
	
	Card eventCard;
	List<Card> cardList;
	Deck discard;
	
	@Before
	public void setup() {
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkCity = newyorkBuilder.build();
		
		cardList = new ArrayList<>();
		cbExecuted = false;
		
		interaction = new MockInteraction();
		interaction.implementSelectCardsFrom(this::selectCardsFrom);
		player = new PlayerImpl(PlayerRole.CONTINGENCY_PLANNER, newyorkCity, new Deck(), interaction);
		planner = new ContingencyPlanner(player);
		
		Event govGrant = new EventGovernmentGrant(null);
		eventCard = new CardEvent(govGrant);
		cardList.add(eventCard);
		
		discard = new Deck();
		discard.putOnTop(eventCard);
	}
	
	@Test
	public void testReuseEventCard() {
		assertFalse(planner.hasEventCardOnRole());
		
		Action action = new ActionSkillContingencyPlanner(planner, interaction, discard);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertTrue(planner.hasEventCardOnRole());
	}
	
	private void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback) {
		assertTrue(cards.contains(eventCard));
		callback.accept(cardList);
	}

}
