package cardActions;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import data.Board;
import data.GameColor;
import game.City;
import game.Game;

public class EpidemicCardAction {
	Board board;
	// Outbreak outbreak;

	public EpidemicCardAction(Board gameBoard) {
		board = gameBoard;
		// outbreak = new Outbreak(board);
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
		City city = board.cities.get(infection);

		GameColor diseaseColor = city.getColor();
		if (!Game.getInstance().isDiseaseEradicated(diseaseColor))
			city.epidemicInfect(diseaseColor);
//		if (!board.eradicatedColor.contains(diseaseColor.compatibility_ColorString)) {
//			
//		}

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
