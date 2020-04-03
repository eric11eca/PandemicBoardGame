package playerAction;

import data.Board;
import game.City;
import player.Player;
import player.PlayerData;

public abstract class PlayerAction {
	public Player player;
	public Board board;

	public PlayerAction(Board board, Player player) {
		this.board = board;
		this.player = player;
	}

	public abstract boolean executeAction();

//	public void eradicate(String diseaseColor) {
//		if (board.remainDiseaseCube.get(diseaseColor) == 24) {
//			board.eradicatedColor.add(diseaseColor);
//		}
//	}

	public void moveTo(City destination) {
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
			pawnData.location = destination;
			destination.currentRoles.add(pawnData.role);
		} else {
			player.playerData.location = destination;
			destination.currentRoles.add(player.playerData.role);
		}
	}
}
