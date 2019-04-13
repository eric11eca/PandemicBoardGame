package Player;

import Initialize.Board;

public class Scientist extends Player {

	public Scientist(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureScientist(gameBoard.curedDiseases);
	}
}
