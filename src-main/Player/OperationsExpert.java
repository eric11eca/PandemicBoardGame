package Player;

import Initialize.Board;

public class OperationsExpert extends Player {
	public OperationsExpert(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
	}

	public void buildResearchStation() {
		location.researchStation = true;
		consumeAction();
	}

	public void moveToAnotherCity(String cityName) {
		for (String cardName : hand.keySet()) {
			if (cardName.equals(cityName)) {
				location = board.cities.get(cityName);
				board.discardPlayerCard.put(cardName, hand.get(cardName));
				hand.remove(cardName);
			}
		}
	}
}
