package gameAction;

import data.Board;
import player.Player;

public class BuildResearchStationCommand implements Command{
	private Board board;
	private Player player;

	public BuildResearchStationCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.buildStation();
	}
}
