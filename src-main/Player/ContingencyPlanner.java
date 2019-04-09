package Player;

import Initialize.Board;

public class ContingencyPlanner extends Player {

	public ContingencyPlanner(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
	}

	public void pickFromDiscardPlayerCard(String cardName) {
		for (String name : board.discardPlayerCard.keySet()) {
			if (name.equals(cardName)) {
				specialEventCard = board.discardPlayerCard.get(name);
				board.discardPlayerCard.remove(name);
				break;
			}
		}
	}
}
