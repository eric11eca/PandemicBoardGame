package Card;

import Initialize.Board;
import Initialize.City;

public class InfectionCardAction {
	private Board board;

	public InfectionCardAction(Board gameBoard) {
		this.board = gameBoard;
	}

	public void drawOneInfactionCard() {
		int top = board.validInfectionCard.size() - 1;
		if (top == -1) {
			throw new RuntimeException("PLAYER LOSS: RUN OUT OF INFACTION CARDS");
		}
		String infectCity = board.validInfectionCard.remove(top);
		String cityColor = board.cities.get(infectCity).color;
		infectCity(infectCity, cityColor);
		board.discardInfectionCard.add(infectCity);
	}

	public void infectCity(String cityName, String diseaseColor) {
		if (board.inQueitNight) {
			board.inQueitNight = false;
			return;
		}
		City city = board.cities.get(cityName);
		int numOfCubes = 1;
		if (city.diseaseCubes.containsKey(diseaseColor)) {
			numOfCubes = city.diseaseCubes.get(diseaseColor);
			numOfCubes += 1;
		}
		city.diseaseCubes.put(diseaseColor, numOfCubes);
		int colorCubes = board.remainDiseaseCube.get(diseaseColor);
		board.remainDiseaseCube.put(diseaseColor, colorCubes - numOfCubes);
	}

}
