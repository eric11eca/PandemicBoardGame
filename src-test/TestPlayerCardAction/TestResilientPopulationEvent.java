package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.ResilientPopulationEvent;
import Initialize.Board;

public class TestResilientPopulationEvent {
	Board board;
	ResilientPopulationEvent resilientPopulationEvent;
	
	@Before
	public void setup() {
		board = new Board();
		String infect1 = "a";
		String infect2 = "b";
		String infect3 = "c";
		board.discardInfectionCard.add(infect1);
		board.discardInfectionCard.add(infect2);
		board.discardInfectionCard.add(infect3);
		resilientPopulationEvent = new ResilientPopulationEvent(board);
	}

	@Test
	public void testresilientPopulation() {
		String infect = "c";
		board.cardRemovedByResilient = infect;
		boolean used = resilientPopulationEvent.resilientPopulation();
		assertTrue(used);
		assertFalse(board.discardInfectionCard.contains(infect));
	}

}
