package Card;

import Initialize.Board;

public class OneQueitNightEvent {
	private Board board;
	
	public OneQueitNightEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public void skipNextInfection() {
		board.inQueitNight = true;
	}

}
