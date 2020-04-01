package cardActions;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import data.Board;
import data.CityOLD;

public class EpidemicCardAction {
	Board board;
	Outbreak outbreak;

	public EpidemicCardAction(Board gameBoard) {
		board = gameBoard;
		outbreak = new Outbreak(board);
	}

	public void increaseInfectionRate() {
		int size = board.infectionRateTracker.size();
		if (size > 1) {
			board.infectionRateTracker.pop();
		}
	}

	public void epidemicInfect() {
		int size = board.validInfectionCards.size();
		String infection = board.validInfectionCards.get(size - 1);
		CityOLD city = board.cities.get(infection);
		String disease = city.getColor().compatibility_ColorString;
		if (!board.eradicatedColor.contains(disease)) {
			int addCubeCount = 0;
			while (city.diseaseCubes.get(disease) < 3) {
				int remainingCubes = board.remainDiseaseCube.get(disease);
				if (remainingCubes == 0) {
					String errorMessage = MessageFormat.format("OutOf{0}", disease);
					throw new RuntimeException(errorMessage);
				}
				int numOfCubes = city.diseaseCubes.get(disease);
				city.diseaseCubes.put(disease, numOfCubes + 1);
				board.remainDiseaseCube.put(disease, remainingCubes - 1);
				addCubeCount++;
			}
			if (addCubeCount < 3) {
				outbreak.performeOutbreak(city);
			}
		}
		board.validInfectionCards.remove(size - 1);
		board.discardInfectionCards.add(infection);
	}

	public boolean reshuffleDiscardInfectionDeck() {
		Collections.shuffle(board.discardInfectionCards);
		return true;
	}

	public void makingNewInfectionCardDeck() {
		List<String> moreInfectionCards = board.discardInfectionCards;
		board.validInfectionCards.addAll(0, moreInfectionCards);
		board.discardInfectionCards.clear();
	}

	public void performEpidemic() {
		increaseInfectionRate();
		epidemicInfect();
		if (reshuffleDiscardInfectionDeck()) {
			makingNewInfectionCardDeck();
		}
	}
}
