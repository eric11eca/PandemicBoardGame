package cards;

import data.Board;

public class ResilientPopulation implements EventCard {
	Board board;
	
	public ResilientPopulation(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		String cardToBeRemoved = board.cardRemovedByResilient;
		board.discardInfectionCards.remove(cardToBeRemoved);
	}
}
