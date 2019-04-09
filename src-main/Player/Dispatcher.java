package Player;

import Initialize.Board;

public class Dispatcher extends Player {

	public Dispatcher(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
	}

}
