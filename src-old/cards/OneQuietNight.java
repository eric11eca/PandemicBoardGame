package cards;

import data.Board;
import game.cards.CardEvent;

public class OneQuietNight implements CardEvent {
	/*
	 * Skip next infection stage
	 */
	private Board board;

	public OneQuietNight(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		board.inQueitNight = true;
	}

}
