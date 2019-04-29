package Card;

import Initialize.Board;

public class ResilientPopulationEvent {
	Board board;
	
	public ResilientPopulationEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public void resilientPopulation() {
		String cardToBeRemoved = board.cardRemovedByResilient;
		board.discardInfectionCard.remove(cardToBeRemoved);
	}
}
