package Card;

import Initialize.Board;
import Initialize.City;

public class GovernmentGrantEvent {
	private Board board;
	
	public GovernmentGrantEvent(Board gameBoard) {
		board = gameBoard;
	}

	public boolean addResearchStation() {
		String cityName = board.cityWithGrant;
		City city = board.cities.get(cityName);
		city.researchStation = true;
		board.cities.put(cityName, city);
		return true;
	}
}
