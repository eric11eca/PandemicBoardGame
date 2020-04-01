package playerAction;

import data.Board;
import player.Player;

public class PlayEventCard extends PlayerAction{

	public PlayEventCard(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		if (player.eventCardName.equals(player.playerData.specialEventCard)) {
			player.playerData.specialEventCard = null;
		} else {
			player.playerData.hand.remove(player.eventCardName);
			board.discardEventCards.add(player.eventCardName);
		}
		board.eventCardAction.executeEventCard(player.eventCardName);
		return false;
	}
}
