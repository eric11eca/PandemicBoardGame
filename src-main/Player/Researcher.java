package Player;

import Initialize.Board;

public class Researcher extends Player {

	public Researcher(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
	}

}
