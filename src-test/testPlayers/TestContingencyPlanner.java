package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import SpeciaoPlayerAction.ContingencyPlannerState;
import data.Board;
import player.PlayerData;

public class TestContingencyPlanner {
	Board board;
	PlayerData contingencyPlannerData;
	ContingencyPlannerState contingencyPlannerState;
	String airlift = "Airlift";
	
	@Before
	public void setup() {
		board = Board.getInstance();
		contingencyPlannerData = new PlayerData();
		contingencyPlannerState = new ContingencyPlannerState(board, contingencyPlannerData);
		contingencyPlannerData.specialSkill = contingencyPlannerState;
	}

	@Test
	public void testPickCardFromDiscardEventPile() {
		board.discardEventCards.add(airlift);
		contingencyPlannerState.cardName = airlift;
		contingencyPlannerData.specialSkill.useSpecialSkill();
		assertEquals(contingencyPlannerData.specialEventCard, airlift);
	}
}
