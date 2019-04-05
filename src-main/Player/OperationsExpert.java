package Player;

import java.util.Random;

import Initialize.Board;
import Initialize.City;

public class OperationsExpert extends Player {
	public OperationsExpert(Board gameBoard) {
		super(gameBoard);
		location = new City();
	}

	public OperationsExpert(Board gameBoard, Random random) {
		super(gameBoard, random);
		discoverCure = new DiscoverCureNormal(hand, gameBoard.curedDiseases);
	}

	public void buildResearchStation() {
		location.researchStation = true;
		action--;
	}

	public void moveToAnotherCity(String cityName) {
		for (String cardName : hand.keySet()) {
			if (cardName.contentEquals(cityName)) {
				location = board.cities.get(cityName);
				board.discardPlayerCard.put(cardName, hand.get(cardName));
				hand.remove(cardName);
			}
		}
	}

}
