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
		if (size > 1) {
			board.infectionRateTrack.pop();
		}
	}

	public void infect() {
		System.out.println("1");
		int size = board.validInfectionCard.size();
		System.out.println("2");
		String infection = board.validInfectionCard.get(size - 1);
		System.out.println("3");
		City city = board.cities.get(infection);
		System.out.println("4");
		String disease = city.color;
		System.out.println("5");
		if (!board.eradicatedDiseases.contains(disease)) {
			System.out.println("6");
			int count = 0;
			while (city.diseaseCubes.get(disease) != 3) {
				System.out.println("7");
				int remainingCubes = board.remainDiseaseCube.get(disease);
				System.out.println("8");
				if (remainingCubes == 0) {
					board.gameEnd = true;
					board.playerLose = true;
					return;
				}
				System.out.println("9");
				int numOfCubes = city.diseaseCubes.get(disease);
				System.out.println("10");
				city.diseaseCubes.put(disease, numOfCubes + 1);
				System.out.println("11");
				board.remainDiseaseCube.put(disease, remainingCubes - 1);
				System.out.println("12");
				count++;
			}
			System.out.println("13");
			if (count != 3) {
				System.out.println("14");
				outbreak.performeOutbreak(city);
			}
		}
		board.validInfectionCard.remove(size - 1);
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
		System.out.println("Before Infection Rate");
		increaseInfectionRate();
		System.out.println("Before Infection");
		infect();
		System.out.println("Before board end");
		if (board.gameEnd == true) {
			return;
		}
		System.out.println("Before Reshuffle");
		if (reshuffleDiscardInfectionDeck()) {
			System.out.println("Before making");
			makingNewInfectionCardDeck();
		}
	}
}
