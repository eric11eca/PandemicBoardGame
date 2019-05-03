package Player;

import Initialize.Board;

public class ResearcherAction {
	private Board board;
	private PlayerData researcher;
	
	public ResearcherAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		researcher = currentPlayer;
		researcher.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		researcher.buildStationModel = new StationBuilderNormal(researcher, board);
	}

}
