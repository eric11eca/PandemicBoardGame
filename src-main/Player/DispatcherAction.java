package Player;

import Initialize.Board;

public class DispatcherAction {
	private Board board;
	private PlayerData dispatcher;
	
	public DispatcherAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		dispatcher = currentPlayer;
		dispatcher.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		dispatcher.buildStationModel = new StationBuilderNormal(dispatcher, board);
	}

}
