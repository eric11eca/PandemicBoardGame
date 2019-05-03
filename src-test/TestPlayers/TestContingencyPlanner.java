package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

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
	String specialCardName = "Airlift";
	@Before
	public void setup() {
		board = new Board();
		contingencyPlannerData = new PlayerData();
		contingencyPlannerData.role = Board.Roles.CONTINGENCYPLANNER;
		contingencyPlanner = new Player(board, contingencyPlannerData);
		contingencyPlannerAction = new ContingencyPlannerAction(board, contingencyPlannerData);
		contingencyPlanner.specialSkill = contingencyPlannerAction;
	}

	@Test
	public void testPickCardFromDispach() {
		String cardName1 = "Chicago";
		contingencyPlannerData.hand.put(cardName1, new PlayerCard(Board.CardType.CITYCARD, cardName1));
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, specialCardName);
		board.discardPlayerCard.put(specialCardName, playerCard);
		contingencyPlannerAction.cardName = specialCardName;
		contingencyPlanner.specialSkill.specialSkill();;
		assertEquals(contingencyPlannerData.specialEventCard.cardName, specialCardName);
		assertFalse(board.discardPlayerCard.containsKey(specialCardName));
	}

	@Test
	public void testRemoveSpecialEventCardCompletly() {
		PlayerCard playerCard = new PlayerCard(Board.CardType.EVENTCARD, specialCardName);
		contingencyPlannerData.specialEventCard = playerCard;
		board.idxofPlayerAirlift = 0;
		board.currentPlayers.add(0, contingencyPlanner);
		String cardName1 = "Chicago";
		String cardName2 = "New York";
		PlayerCard playerCard1 = new PlayerCard(Board.CardType.CITYCARD, cardName1);
		PlayerCard playerCard2 = new PlayerCard(Board.CardType.CITYCARD, cardName2);
		board.discardPlayerCard.put(cardName1, playerCard1);
		board.discardPlayerCard.put(cardName2, playerCard2);
		int old_size = board.discardPlayerCard.size();

		boolean cardUsed = contingencyPlanner.useEventCard(specialCardName);
		assertTrue(cardUsed);
		assertTrue(contingencyPlannerData.specialEventCard == null);
		int new_size = board.discardPlayerCard.size();
		assertEquals(old_size, new_size);
	}

}
