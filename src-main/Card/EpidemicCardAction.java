package Card;

import Initialize.Board;

public class EpidemicCardAction {
	Board board;
	
	public EpidemicCardAction(Board gameBoard) {
		board = gameBoard;
	}

	public void increaseInfectionRate() {
		int size = board.infectionRateTrack.size();
		if(size > 1) {
			board.infectionRateTrack.pop();
		}
	}
	
	
}
