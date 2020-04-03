package cards;

import data.Board;
import game.City;
import game.cards.CardEvent;

public class GovernmentGrant implements CardEvent {
	/*
	 * Built a research station anywhere
	 */
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
