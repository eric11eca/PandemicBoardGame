package cards;

import data.Board;

public class OneQuietNight implements EventCard{
	private Board board;
	
	public OneQuietNight(Board gameBoard) {
		board = gameBoard;
	}


	@Override
	public void executeEvent() {
		board.inQueitNight = true;
	}

}
