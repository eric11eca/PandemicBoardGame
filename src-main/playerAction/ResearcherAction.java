package playerAction;

import data.Board;
import player.DiscoverCureNormal;
import player.PlayerData;
import player.StationBuilderNormal;
import player.TreatNormal;

public class ResearcherAction {
	private Board board;
	private PlayerData researcher;
	
	public ResearcherAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		researcher = currentPlayer;
		researcher.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		researcher.buildStationModel = new StationBuilderNormal(researcher, board);
		researcher.treatAction = new TreatNormal(currentPlayer, gameBoard);
	}

}
