package Card;

import Initialize.Board;
import Initialize.City;

public class GovernmentGrantEvent implements EventCard{
	private Board board;
	
	public GovernmentGrantEvent(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		String cityName = board.cityWithGrant;
		City city = board.cities.get(cityName);
		city.researchStation = true;
		board.cities.put(cityName, city);
	}
}
