package playerAction;

import data.Board;
import player.Player;

public class TreatDisease extends PlayerAction{

	public TreatDisease(Board board, Player player) {
		super(board, player);
	}
	
	@Override
	public boolean executeAction() {
		player.playerData.treatAction.treat(player.diseaseTobeTreated);
		eradicate(player.diseaseTobeTreated);
		player.consumeAction();
		return false;
	}
}
