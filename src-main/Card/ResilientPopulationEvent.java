package Card;

import Initialize.Board;

public class ResilientPopulationEvent implements EventCard {
	Board board;
	
	public ResilientPopulationEvent(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		String cardToBeRemoved = board.cardRemovedByResilient;
		board.discardInfectionCard.remove(cardToBeRemoved);
	}
}
