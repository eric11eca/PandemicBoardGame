package testPlayers;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

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
		contingencyPlannerAction = new ContingencyPlannerAction(board, contingencyPlannerData);
		contingencyPlannerData.specialSkill = contingencyPlannerAction;
	}

	@Test
	public void testPickCardFromDiscardEventPile() {
		board.discardEventCards.add(airlift);
		contingencyPlannerAction.cardName = airlift;
		contingencyPlannerData.specialSkill.useSpecialSkill();
		assertEquals(contingencyPlannerData.specialEventCard, airlift);
	}
}
