package Player;

import Initialize.Board;

public class DispatcherAction {
	private Board board;
	private PlayerData dispatcher;
	private Player player;
	
	public DispatcherAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		dispatcher = currentPlayer;
		player = new Player(board, dispatcher);
		dispatcher.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		dispatcher.buildStationModel = new StationBuilderNormal(dispatcher, board);
	}

}
