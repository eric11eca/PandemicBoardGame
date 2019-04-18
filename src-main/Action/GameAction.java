package Action;

import Card.PlayerCard;
import Initialize.Board;
import Player.Player;

public class GameAction {
	Board board;
	Player currentPlayer;
	
	public GameAction(Board gameBoard) {
		board = gameBoard;
		int currentPlayerIdx = board.currentPlayerIndex;
		currentPlayer = board.currentPlayers.get(currentPlayerIdx);
	}
	
	public boolean drawTwoPlayerCards() {
		for(int i = 0; i < 2; i++) {
			PlayerCard playerCard = board.validPlayerCard.get(0);
			currentPlayer.receiveCard(playerCard);
			board.validPlayerCard.remove(0);
			board.discardPlayerCard.put(playerCard.cardName, playerCard);
		} 
		return true;
	}
	
	public void OneTurn() {
		
	}
}