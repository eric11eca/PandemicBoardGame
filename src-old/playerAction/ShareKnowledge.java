package playerAction;

import data.Board;
import player.Player;

public class ShareKnowledge extends PlayerAction{

	public ShareKnowledge(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		if (board.cityToShare.cardType != Board.CardType.CITYCARD) {
			throw new RuntimeException("CantUseEventCardException");
		}
		if (board.isGiving) {
			player.giveCard(player, board.playerToShare, board.cityToShare);
		} else {
			player.giveCard(board.playerToShare, player, board.cityToShare);
		}
		player.consumeAction();
		return false;
	}
}
