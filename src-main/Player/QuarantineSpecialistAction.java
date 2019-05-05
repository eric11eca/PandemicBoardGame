package Player;

import Initialize.Board;

public class QuarantineSpecialistAction {
	private Board board;
	private PlayerData quarantineSpecialist;
	
	public QuarantineSpecialistAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		quarantineSpecialist = currentPlayerData;
		quarantineSpecialist.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialist.buildStationModel = new StationBuilderNormal(quarantineSpecialist, board);
		quarantineSpecialist.treatAction = new TreatNormal(quarantineSpecialist, board);
	}

}
