package game.event;

import data.Board;

public class EventOneQuietNight implements Event {
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
