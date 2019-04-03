package Player;

import Card.PlayerCard;
import Initialize.Board;

public class ContingencyPlanner extends Player{
	Board board;
		
	public ContingencyPlanner(Board board) {
		this.board = board;
	}

	public void pickFromDiscardPlayerCard(String cardName) {
		for (PlayerCard playerCard : board.discardPlayerCard) {
			if (playerCard.cardName == cardName) {
				hand.add(hand.size(), playerCard);
				break;
			}
		} 
	}
	
	

}
