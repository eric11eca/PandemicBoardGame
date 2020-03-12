package gameAction;

import data.Board;
import player.Player;

public class CureDiseaseCommand implements Command{
	private Board board;
	private Player player;
	
	public CureDiseaseCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.discoverCure(board.cardsToCureDisease);
	}
}
