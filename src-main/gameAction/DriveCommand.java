package gameAction;

import data.Board;
import data.City;
import player.Player;

public class DriveCommand implements Command{
	private Board board;
	private Player player;

	public DriveCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		City driveDestination = board.cities.get(board.driveDestinationName);
		board.doesChangeLocation = true;
		player.drive(driveDestination);
	}
}
