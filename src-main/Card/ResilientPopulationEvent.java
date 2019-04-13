package Card;

import Initialize.Board;

public class ResilientPopulationEvent {
	Board board;
	
	public ResilientPopulationEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public boolean resilientPopulation() {
		String cardToBeRemoved = board.cardRemovedByResilient;
		board.discardInfectionCard.remove(cardToBeRemoved);
		return true;
	}
}
