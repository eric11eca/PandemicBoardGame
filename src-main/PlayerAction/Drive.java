package PlayerAction;

import data.Board;
import data.City;
import player.PlayerData;

public class Drive extends Mobility{
	
	public Drive(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
		drive = true;
	}

	@Override
	public boolean checkConditions() {
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData; 
			if (pawnData.location.neighbors.containsKey(destination.cityName)) {
				return true;
			}
		} else if (playerData.location.neighbors.containsKey(destination.cityName)) {
			return true;
		}
		return false;
	}

	@Override
	public void setDestination(City dest) {
		destination = dest;
	}
}

