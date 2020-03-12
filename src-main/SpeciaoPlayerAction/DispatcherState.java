package SpeciaoPlayerAction;

import data.Board;
import data.City;

public class DispatcherState implements SpecialSkill {
	private Board board;

	public DispatcherState() {
		board = Board.getInstance();
	}

	public void moveOtherPlayer() {
		City newLocation = board.cities.get(board.newLocationName);
		board.currentPlayers.get(board.pawnTobeMoved).playerData.location = newLocation;
	}

	@Override
	public void applySkill() {
		if (board.dispatcherCase == 0) {
			moveOtherPlayer();
		}
		board.dispatcherCase = -1;
	}

}
