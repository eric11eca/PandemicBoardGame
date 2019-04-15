package Card;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import Initialize.Board;
import Initialize.City;

public class EpidemicCardAction {
	Board board;
	
	public EpidemicCardAction(Board gameBoard) {
		board = gameBoard;
	}

	public void increaseInfectionRate() {
		int size = board.infectionRateTrack.size();
		if(size > 1) {
			board.infectionRateTrack.pop();
		}
	}

	public void infect() {
		int size = board.validInfectionCard.size();
		String infection = board.validInfectionCard.get(size-1);
		City city = board.cities.get(infection);
		String disease = city.color;
		if(!board.eradicatedDiseases.contains(disease)) {
			while(city.diseaseCubes.get(disease) != 3) {
				int numOfCubes = city.diseaseCubes.get(disease);
				city.diseaseCubes.put(disease, numOfCubes+1);
			}
		}
	}
	
	public void reshuffleDiscardInfectionDeck() {
		Collections.shuffle(board.discardInfectionCard);
	}
	
	public void makingNewInfectionCardDeck() {
		List<String> moreInfectionCards = board.discardInfectionCard;
		board.validInfectionCard.addAll(0, moreInfectionCards);
		board.discardInfectionCard.clear();
	}
	
	
	
	
}
