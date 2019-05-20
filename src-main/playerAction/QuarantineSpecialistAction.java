package playerAction;

import data.Board;
import player.DiscoverCureNormal;
import player.PlayerData;
import player.StationBuilderNormal;
import player.TreatNormal;

public class QuarantineSpecialistAction {
	private Board board;
	private PlayerData quarantineSpecialist;
	
	public QuarantineSpecialistAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		quarantineSpecialist = currentPlayerData;
		quarantineSpecialist.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialist.buildStationModel = new StationBuilderNormal(quarantineSpecialist, board);
		quarantineSpecialist.treatAction = new TreatNormal(quarantineSpecialist, board);
	}

}
