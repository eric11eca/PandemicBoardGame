package game.cards.event;

import data.Board;

public class EventOneQuietNight implements CardEvent {
	/*
	 * Skip next infection stage
	 */
	private Board board;

	public EventOneQuietNight(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		board.inQueitNight = true;
	}

}
