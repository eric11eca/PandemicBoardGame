package game.cards;

import java.text.MessageFormat;

import data.Board;
import data.GameColor;
import game.City;
import game.Game;

public class InfectionCardAction {
	private Board board;
	// private Outbreak outbreak;

	public InfectionCardAction(Board gameBoard) {
		this.board = gameBoard;
		// outbreak = new Outbreak(board);
	}

	public void drawOneInfectionCard() {
		int top = board.validInfectionCards.size() - 1;
		if (top == -1) {
			throw new RuntimeException("NoInfectionCards");
		}
		String infectCity = board.validInfectionCards.remove(top);
		GameColor cityColor = board.cities.get(infectCity).getColor();

		infectCity(infectCity, cityColor);
		board.discardInfectionCards.add(infectCity);
	}

	public void infectCity(String cityName, GameColor diseaseColor) {
		if (Game.getInstance().isDiseaseEradicated(diseaseColor))
			return;

		City city = board.cities.get(cityName);

		city.infect(diseaseColor);
	}

}
