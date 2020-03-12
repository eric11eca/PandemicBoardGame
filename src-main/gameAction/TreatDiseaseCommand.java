package gameAction;

import data.Board;
import player.Player;

public class TreatDiseaseCommand implements Command{
	private Board board;
	private Player player;

	public TreatDiseaseCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.treat(board.diseaseBeingTreated);
	}
}
