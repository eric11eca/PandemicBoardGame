package cards;

import data.Board;
import game.cards.CardEvent;

public class ResilientPopulation implements CardEvent {
	/*
	 * Remove 1 card from the infection discard pile
	 */
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
