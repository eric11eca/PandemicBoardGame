package testPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import data.Board;
import game.event.EventResilientPopulation;

public class TestResilientPopulationEvent {
	Board board;
	EventResilientPopulation resilientPopulationEvent;
	
	@Before
	public void setup() {
		board = new Board();
		String infect1 = "a";
		String infect2 = "b";
		String infect3 = "c";
		board.discardInfectionCards.add(infect1);
		board.discardInfectionCards.add(infect2);
		board.discardInfectionCards.add(infect3);
		resilientPopulationEvent = new EventResilientPopulation(board);
	}

	@Test
	public void testresilientPopulation() {
		String infect = "c";
		board.cardRemovedByResilient = infect;
		resilientPopulationEvent.executeEvent();
		assertFalse(board.discardInfectionCards.contains(infect));
	}

}
