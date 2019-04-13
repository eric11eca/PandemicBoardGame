package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.EpidemicCardAction;
import Initialize.Board;

public class TestEpidemicCardAction {

	Board board;
	EpidemicCardAction epidemicCardAction;
	
	@Before 
	public void setup() {
		board = new Board();
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
		epidemicCardAction = new EpidemicCardAction(board);
	}
	
	@Test
	public void testInfectionRateIncrease() {
		epidemicCardAction.increaseInfectionRate();
		assertTrue(2 == board.infectionRateTrack.peek());
	}
	
	@Test
	public void testInfectionRateIncreaseWhenIsMax() {
		for (int i = 0; i < 6; i++) {
			epidemicCardAction.increaseInfectionRate();
		}
		assertTrue(4 == board.infectionRateTrack.peek());
		epidemicCardAction.increaseInfectionRate();
		assertTrue(4 == board.infectionRateTrack.peek());
	}

}
