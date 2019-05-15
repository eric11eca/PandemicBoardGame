package playerAction;

import data.Board;
import data.City;

public class DispatcherAction implements SpecialSkill {
	private Board board;

	public DispatcherAction(Board gameBoard) {
		board = gameBoard;
	}

	public void moveOtherPlayer() {
		City newLocation = board.cities.get(board.newLocationName);
		board.currentPlayers.get(board.pawnTobeMoved).playerData.location = newLocation;
	}

	@Override
	public void specialSkill() {
		if (board.dispatcherCase == 0) {
			moveOtherPlayer();
		}
		board.dispatcherCase = -1;
	}

}
