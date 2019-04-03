package Player;

import Card.PlayerCard;
import Initialize.Board;

public class ContingencyPlanner extends Player{
		
	public ContingencyPlanner(Board gameBoard) {
		super(gameBoard);
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
