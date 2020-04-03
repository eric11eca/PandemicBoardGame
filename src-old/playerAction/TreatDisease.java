package playerAction;

import data.Board;
import data.GameColor;
import game.player.Player;

public class TreatDisease extends PlayerAction {

	public TreatDisease(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		player.playerData.treatAction.treat(player.playerData.location,
				GameColor.compatibility_getByName(player.diseaseTobeTreated));
		// eradicate(player.diseaseTobeTreated);
		player.consumeAction();
		return false;
	}
}
