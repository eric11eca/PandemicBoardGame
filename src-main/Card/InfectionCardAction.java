package Card;

import Initialize.Board;
import Initialize.City;

public class InfectionCardAction {
	private Board board;
	
	public InfectionCardAction(Board gameBoard) {
		this.board = gameBoard;
	}
	
	public void infectCity(String cityName, String diseaseColor) {
		City city = board.cities.get(cityName);
		int numOfCubes = 1;
		if(city.diseaseCubes.containsKey(diseaseColor)) {
			numOfCubes = city.diseaseCubes.get(diseaseColor);
			numOfCubes += 1;
		}

		city.diseaseCubes.put(diseaseColor, numOfCubes);
	}
	
}
