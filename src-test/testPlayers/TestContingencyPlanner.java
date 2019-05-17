package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import data.Board;
import player.Player;
import player.PlayerData;
import playerAction.ContingencyPlannerAction;

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
		contingencyPlannerAction = new ContingencyPlannerAction(board, contingencyPlannerData);
		contingencyPlannerData.specialSkill = contingencyPlannerAction;
		contingencyPlanner = new Player(board, contingencyPlannerData);
	}

	@Test
	public void testPickCardFromDiscardEventPile() {
		board.discardEventCards.add(airlift);
		contingencyPlannerAction.cardName = airlift;
		contingencyPlannerData.specialSkill.useSpecialSkill();
		assertEquals(contingencyPlannerData.roleCard, airlift);
	}
	
	@Test
	public void testUseRoleCard() {
		contingencyPlannerData.roleCard = airlift;
		board.idxofPlayerAirlift = 0;
		board.currentPlayers.add(this.contingencyPlanner);
		
		int old_size = board.discardEventCards.size();
		contingencyPlanner.useEventCard(airlift);
		assertTrue(contingencyPlannerData.roleCard == null);
		int new_size = board.discardEventCards.size();
		assertEquals(old_size, new_size);
	}

}
