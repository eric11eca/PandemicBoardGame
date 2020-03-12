package PlayerAction;

import cards.PlayerCard;
import data.Board;
import player.PlayerData;

public class CommonPlayerAction {
	private Board board;
	private PlayerData playerData;
	
	public CommonPlayerAction(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}
	
	public void useEventCard(String cardName) {
		if (cardName.equals(playerData.specialEventCard)) {
			playerData.specialEventCard = null;
		} else {
			playerData.hand.remove(cardName);
			board.discardEventCards.add(cardName);
		}
		board.eventCardAction.executeEventCard(cardName);
	}
	
	public void receiveCard(PlayerCard playerCard) {
		playerData.hand.put(playerCard.cardName, playerCard);
	}
	
	public void consumeAction() {
		playerData.action -= 1;
	}
	
	public void discardCard() {
		for (int i = 0; i < board.cardToBeDiscard.size(); i++) {
			String cardName = board.cardToBeDiscard.get(i);
			if (playerData.hand.containsKey(cardName)) {
				playerData.hand.remove(cardName);
			}
		}
		board.cardToBeDiscard.clear();
	}
	
	public void eradicate(String diseaseColor) {
		if (board.remainDiseaseCube.get(diseaseColor) == 24) {
			board.eradicatedColor.add(diseaseColor);
		}
	}
}
