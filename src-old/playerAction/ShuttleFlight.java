package playerAction;

import data.Board;
import player.Player;

public class ShuttleFlight extends PlayerAction{

	public ShuttleFlight(Board board, Player player) {
		super(board, player);
	}
	
	@Override
	public boolean executeAction() {
		if (player.playerData.location.researchStation) {
			if (player.destination.researchStation) {
				moveTo(player.destination);
				player.consumeAction();
			}
		}
		return true;
	}
}
