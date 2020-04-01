package cardActions;

import java.text.MessageFormat;

import data.Board;
import game.City;

public class InfectionCardAction {
	private Board board;
	private Outbreak outbreak;

	public InfectionCardAction(Board gameBoard) {
		this.board = gameBoard;
		outbreak = new Outbreak(board);
	}

	public void drawOneInfectionCard() {
		int top = board.validInfectionCards.size() - 1;
		if (top == -1) {
			throw new RuntimeException("NoInfectionCards");
		}
		String infectCity = board.validInfectionCards.remove(top);
		String cityColor = board.cities.get(infectCity).getColor().compatibility_ColorString;

		infectCity(infectCity, cityColor);
		board.discardInfectionCards.add(infectCity);
	}

	public void infectCity(String cityName, String diseaseColor) {

		if (board.eradicatedColor.contains(diseaseColor)) {
			return;
		}

		City city = board.cities.get(cityName);

		if (city.currentRoles.contains(Board.Roles.QUARANTINESPECIALIST)) {
			return;
		}

		for (City neighbor : city.neighbors) {
			if (neighbor.currentRoles.contains(Board.Roles.QUARANTINESPECIALIST)) {
				return;
			}
		}

		int numOfCubes = 1;
		if (city.diseaseCubes.containsKey(diseaseColor)) {
			numOfCubes = city.diseaseCubes.get(diseaseColor);
			numOfCubes += 1;
		}

		if (numOfCubes > 3) {
			outbreak.performeOutbreak(city);
			return;
		}

		city.diseaseCubes.put(diseaseColor, numOfCubes);
		int colorCubes = board.remainDiseaseCube.get(diseaseColor);

		board.remainDiseaseCube.put(diseaseColor, colorCubes - numOfCubes);
	}

}
