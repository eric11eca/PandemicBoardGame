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
		for (String name : board.discardPlayerCard.keySet()) {
			if (name == cardName) {
				specialEventCard = board.discardPlayerCard.get(name);
				board.discardPlayerCard.remove(name);
				break;
			}
		}
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}
	
	

}
