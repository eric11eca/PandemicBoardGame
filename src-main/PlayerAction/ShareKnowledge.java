package PlayerAction;

import cards.PlayerCard;
import data.Board;
import player.Player;

public class ShareKnowledge {
	private Board board;
	
	public ShareKnowledge() {
		board = Board.getInstance();
	}
	
	public void shareKnowledge(Player player) {
		if (board.cityToShare.cardType != Board.CardType.CITYCARD) {
			throw new RuntimeException("CantUseEventCardException");
		}
		if (board.isGiving) {
			giveCard(player, board.playerToShare, board.cityToShare);
		} else {
			giveCard(board.playerToShare, player, board.cityToShare);
		}
	}
	
	private void giveCard(Player giver, Player receiver, PlayerCard citycard) {
		if(giver.playerData.role != Board.Roles.RESEARCHER 
				&& !citycard.cardName.equals(giver.playerData.location.cityName)) {
			throw new RuntimeException("CanNotShareKnowledgeException");
		}
		board.cardToBeDiscard.add(citycard.cardName);
		giver.commonAction.discardCard();
		receiver.receiveCard(citycard);
	}
}
