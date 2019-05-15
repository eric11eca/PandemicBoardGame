package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import cards.ForecastEvent;
import data.Board;
import player.PlayerData;

public class TestForecastEvent {
	Board board;
	ForecastEvent forecast;
	PlayerData playerData;
	
	@Before 
	public void setup() {
		board = new Board();
		forecast = new ForecastEvent(board);
		setupBoard();
	}
	
	public void setupBoard() {
		board.validInfectionCards.add("NewYork");
		board.validInfectionCards.add("TaiPei");
		board.validInfectionCards.add("Chicago");
		board.validInfectionCards.add("Moscow");
		board.validInfectionCards.add("Cairo");
		board.validInfectionCards.add("Seattle");
		
		board.rearrangeInstruction.add("Cairo");
		board.rearrangeInstruction.add("Moscow");
		board.rearrangeInstruction.add("NewYork");
		board.rearrangeInstruction.add("TaiPei");
		board.rearrangeInstruction.add("Seattle");
		board.rearrangeInstruction.add("Chicago");
	}
	
	@Test 
	public void testForecast() {
		board.validInfectionCards.clear();
		forecast.executeEvent();
		assertEquals(6, board.validInfectionCards.size());
		assertEquals("Cairo", board.validInfectionCards.get(0));
		assertEquals("TaiPei", board.validInfectionCards.get(3));
	}
	
	@Test
	public void testArrangeCardPhase() {
		board.validInfectionCards.clear();
		forecast.arrangeCard();
		assertEquals("Cairo", board.validInfectionCards.get(0));
		assertEquals("TaiPei", board.validInfectionCards.get(3));
	}
}
