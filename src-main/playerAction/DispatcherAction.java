package playerAction;

import data.Board;
import player.DiscoverCureNormal;
import player.PlayerData;
import player.StationBuilderNormal;
import player.TreatNormal;

public class DispatcherAction {
	private Board board;
	private PlayerData dispatcher;
	
	public DispatcherAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		dispatcher = currentPlayerData;
		dispatcher.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		dispatcher.buildStationModel = new StationBuilderNormal(dispatcher, board);
		dispatcher.treatAction = new TreatNormal(dispatcher, board);
	}

}
