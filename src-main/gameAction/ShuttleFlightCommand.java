package gameAction;

import data.Board;
import data.City;
import player.Player;

public class ShuttleFlightCommand implements Command{
	private Board board;
	private Player player;

	public ShuttleFlightCommand() {
		board = Board.getInstance();
		player = board.currentPlayer;
	}

	@Override
	public void execute() {
		City shuttleDestination = board.cities.get(board.shuttleDestinationName);
		player.shuttleFlight(shuttleDestination);
		board.doesChangeLocation = true;
	}
}
