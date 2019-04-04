package Player;

import Initialize.Board;

import Initialize.Board;
import Initialize.City;

public class OperationsExpert extends Player {
	public OperationsExpert(Board gameBoard) {
		super(gameBoard);
		location = new City();
	}

	public void buildResearchStation() {
		location.researchStation = true;
		action--;
	}

	public void moveToAnotherCity(String cityName) {
		for (String cardName : hand.keySet()) {
			if (cardName == cityName) {
				location = board.cities.get(cityName);
				board.discardPlayerCard.put(cardName, hand.get(cardName));
				hand.remove(cardName);
			}
		}
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}

}
