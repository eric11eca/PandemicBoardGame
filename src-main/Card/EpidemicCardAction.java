package Card;

import java.util.Collections;
import java.util.List;

import Initialize.Board;
import Initialize.City;

public class EpidemicCardAction {
	Board board;
	Outbreak outbreak;
	
	public EpidemicCardAction(Board gameBoard) {
		board = gameBoard;
		outbreak = new Outbreak(board);
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
				int remainingCubes = board.remainDiseaseCube.get(disease);
				if(remainingCubes == 0){
					board.gameEnd = true;
					board.playerLose = true;
					return;
				}
				int numOfCubes = city.diseaseCubes.get(disease);
				city.diseaseCubes.put(disease, numOfCubes+1);	
				board.remainDiseaseCube.put(disease, remainingCubes-1);
			}
			outbreak.performeOutbreak(city);
		}
		board.validInfectionCard.remove(size-1);
		board.discardInfectionCard.add(infection);
	}
	
	public boolean reshuffleDiscardInfectionDeck() {
		Collections.shuffle(board.discardInfectionCard);
		return true;
	}
	
	public void makingNewInfectionCardDeck() {
		List<String> moreInfectionCards = board.discardInfectionCard;
		board.validInfectionCard.addAll(0, moreInfectionCards);
		board.discardInfectionCard.clear();
	}
	
	public void performeEpidemic() {
		increaseInfectionRate();
		infect();
		if(board.gameEnd == true) {
			return;
		}
		if(reshuffleDiscardInfectionDeck()) {
			makingNewInfectionCardDeck();
		}
	}
}
