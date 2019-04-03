package Player;

import Card.PlayerCard;
import Initialize.Board;

public class ContingencyPlanner extends Player{
	Board board;

	public ContingencyPlanner(Board board) {
		this.board = board;
	}

	@Override
	public void removeAllCubes() {}

	@Override
	public void pickFromDiscardPlayerCard(String cardName) {
		for (PlayerCard playerCard : board.discardPlayerCard) {
			if (playerCard.cardName == cardName) {
				specialEventCard = playerCard;
				break;
			}
		} 
	}
	
	
}
