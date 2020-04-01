package cards;

import data.Board;
import game.city.City;

public class GovernmentGrant implements EventCard {
	private Board board;

	public GovernmentGrant(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		String cityName = board.cityWithGrant;
		City city = board.cities.get(cityName);
		city.buildResearchStation();
		board.currentResearchStation.put(city.getName(), city);
	}
}
