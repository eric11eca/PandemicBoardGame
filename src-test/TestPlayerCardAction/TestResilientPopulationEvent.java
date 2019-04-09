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
		resilientPopulationEvent = new ResilientPopulationEvent(board);
	}

	@Test
	public void testresilientPopulation() {
		boolean used = resilientPopulationEvent.resilientPopulation();
		assertTrue(used);
	}

}
