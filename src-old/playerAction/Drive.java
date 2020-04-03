package playerAction;

import data.Board;
import game.City;
import game.player.Player;
import player.PlayerData;

public class Drive extends PlayerAction {

	public Drive(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		City destination = board.driveDestination;
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
			if (pawnData.location.isNeighbor(destination)) {
				moveTo(destination);
				player.consumeAction();
			}
		} else if (player.playerData.location.isNeighbor(destination)) {
			moveTo(destination);
			player.consumeAction();
		}
		return true;
	}
}
