package gameAction;

import data.Board;
import player.Player;

public class CharterFlightCommand implements Command{
	private Board board;
	private Player player;

	public CharterFlightCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.charterFlight();
		board.doesChangeLocation = true;
	}
}
