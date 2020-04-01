package playerAction;

import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class Drive extends PlayerAction{

	public Drive(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		City destination = board.cities.get(board.driveDestinationName);
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
			if (pawnData.location.neighbors.containsKey(destination.cityName)) {
				moveTo(destination);
				player.consumeAction();
			}
		} else if (player.playerData.location.neighbors.containsKey(destination.cityName)) {
			moveTo(destination);
			player.consumeAction();
		}
		return true;
	}
}
