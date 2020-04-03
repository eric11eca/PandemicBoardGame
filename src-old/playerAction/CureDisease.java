package playerAction;

import cards.PlayerCard;
import data.Board;
import game.player.Player;

public class CureDisease extends PlayerAction {

	public CureDisease(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		boolean isResearchStation = player.playerData.location.hasResearchStation();
		if (isResearchStation) {
			if (player.playerData.discoverCureModel.discover(player.cardsToCureDisease)) {
				for (PlayerCard playercard : player.cardsToCureDisease) {
					board.cardToBeDiscard.add(playercard.cardName);
				}
				player.consumeAction();
			}
			if (board.curedDiseases.size() == 4) {
				throw new RuntimeException("PlayerWinException");
			}

			// eradicate(player.cardsToCureDisease.get(0).color);
			player.discardCard();
		} else {
			throw new RuntimeException("NoStationException");
		}
		return false;
	}
}
