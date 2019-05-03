package Player;

import Initialize.Board;

public class QuarantineSpecialistAction {
	private Board board;
	private PlayerData quarantineSpecialist;
	
	public QuarantineSpecialistAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		quarantineSpecialist = currentPlayer;
		quarantineSpecialist.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialist.buildStationModel = new StationBuilderNormal(quarantineSpecialist, board);
	}

}
