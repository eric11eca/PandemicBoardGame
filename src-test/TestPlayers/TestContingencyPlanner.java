package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Card.EventCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Player.ContingencyPlannerAction;
import Player.PlayerData;
import Player.Player;

public class TestContingencyPlanner {
	Board board;
	Player contingencyPlanner;
	PlayerData contingencyPlannerData;
	ContingencyPlannerAction contingencyPlannerAction;
	String airlift = "Airlift";
	
	@Before
	public void setup() {
		board = new Board();
		contingencyPlannerData = new PlayerData();
		contingencyPlannerData.role = Board.Roles.CONTINGENCYPLANNER;
		board.eventCardAction = new EventCardAction(board);
		contingencyPlanner = new Player(board, contingencyPlannerData);
		contingencyPlannerAction = new ContingencyPlannerAction(board, contingencyPlannerData);
		contingencyPlanner.specialSkill = contingencyPlannerAction;
		
		
	}

	@Test
	public void testPickCardFromDiscardEventPile() {
		board.discardEventCards.add(airlift);
		contingencyPlannerAction.cardName = airlift;
		contingencyPlanner.specialSkill.specialSkill();
		assertEquals(contingencyPlannerData.roleCard, airlift);
		assertFalse(board.discardCityCards.containsKey(airlift));
	}
	
	@Test
	public void testUseRoleCard() {
		contingencyPlannerData.roleCard = airlift;
		board.idxofPlayerAirlift = 0;
		board.currentPlayers.add(this.contingencyPlanner);
		String cardName1 = "Chicago";
		String cardName2 = "New York";
		PlayerCard playerCard1 = new PlayerCard(Board.CardType.CITYCARD, cardName1);
		PlayerCard playerCard2 = new PlayerCard(Board.CardType.CITYCARD, cardName2);
		board.discardCityCards.put(cardName1, playerCard1);
		board.discardCityCards.put(cardName2, playerCard2);
		
		int old_size = board.discardEventCards.size();
		contingencyPlanner.useEventCard(airlift);
		assertTrue(contingencyPlannerData.roleCard == null);
		int new_size = board.discardEventCards.size();
		assertEquals(old_size, new_size);
	}

}
