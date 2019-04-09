package Card;

import Initialize.Board;

public class OneQueitNightEvent {
	private Board board;
	
	public OneQueitNightEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public boolean skipNextInfection() {
		board.inQueitNight = true;
		return true;
	}

}
