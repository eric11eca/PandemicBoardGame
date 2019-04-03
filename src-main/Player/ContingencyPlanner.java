package Player;

import java.util.Random;

import Card.PlayerCard;
import Initialize.Board;

public class ContingencyPlanner extends Player {

	public ContingencyPlanner(Board gameBoard) {
		super(gameBoard);
	}

	public ContingencyPlanner(Board gameBoard, Random random) {
		super(gameBoard, random);
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
