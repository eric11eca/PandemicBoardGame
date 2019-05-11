package playerAction;

import initialize.Board;
import player.PlayerData;

public class ResearcherAction {
	private Board board;
	private PlayerData researcher;
	
	public ResearcherAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		researcher = currentPlayer;
	}

}
