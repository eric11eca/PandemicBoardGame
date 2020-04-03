package playerAction;

import data.Board;
import game.player.Player;

public class ShuttleFlight extends PlayerAction {

	public ShuttleFlight(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		if (player.playerData.location.hasResearchStation()) {
			if (player.destination.hasResearchStation()) {
				moveTo(player.destination);
				player.consumeAction();
			}
		}
		return true;
	}
}
