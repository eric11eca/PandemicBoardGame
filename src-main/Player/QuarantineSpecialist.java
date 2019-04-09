package Player;

import Initialize.Board;

public class QuarantineSpecialist extends Player {

	public QuarantineSpecialist(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
	}

}
