package playerAction;

import data.Board;
import data.CityOLD;

public class DispatcherAction implements SpecialSkill {
	private Board board;

	public DispatcherAction(Board gameBoard) {
		board = gameBoard;
	}

	public void moveOtherPlayer() {
		CityOLD newLocation = board.cities.get(board.newLocationName);
		board.currentPlayers.get(board.pawnTobeMoved).playerData.location = newLocation;
	}

	@Override
	public void useSpecialSkill() {
		if (board.dispatcherCase == 0) {
			moveOtherPlayer();
		}
		board.dispatcherCase = -1;
	}

}
