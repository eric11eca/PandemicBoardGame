package cards;

import initialize.Board;
import initialize.City;

public class GovernmentGrant implements EventCard{
	private Board board;
	
	public GovernmentGrant(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		String cityName = board.cityWithGrant;
		City city = board.cities.get(cityName);
		city.researchStation = true;
		board.currentResearchStation.put(city.cityName, city);
		//board.cities.put(cityName, city);
	}
}