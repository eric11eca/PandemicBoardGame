package Card;

import Initialize.Board;

public class OneQuietNightEvent implements EventCard{
	private Board board;
	
	public OneQuietNightEvent(Board gameBoard) {
		board = gameBoard;
	}


	@Override
	public void executeEvent() {
		board.inQueitNight = true;
	}

}
