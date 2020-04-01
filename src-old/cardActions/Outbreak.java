package cardActions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.CityOLD;

public class Outbreak {
	Board board;

	public Outbreak(Board gameBoard) {
		board = gameBoard;
	}

	public void moveOutbreakMarkForward() {
		board.outbreakMark += 1;
		if (board.outbreakMark == 8) {
			throw new RuntimeException("OutbreakException");
		}
	}

	public List<CityOLD> infectConnectedCities(CityOLD currentCity) {
		String disease = currentCity.getColor().compatibility_ColorString;
		List<CityOLD> continueOutbreak = new ArrayList<>();
		for (CityOLD city : currentCity.neighbors) {
			if (!city.isInOutbreak) {
				int currentNum = city.diseaseCubes.get(disease);
				if (currentNum >= 3) {
					continueOutbreak.add(city);
				} else {
					if (board.remainDiseaseCube.get(disease) == 0) {
						String errorMessage = MessageFormat.format("OutOf{0}", disease);
						throw new RuntimeException(errorMessage);
					}
					city.diseaseCubes.put(disease, currentNum + 1);
					int remainingCube = board.remainDiseaseCube.get(disease);
					board.remainDiseaseCube.put(disease, remainingCube - 1);
					if (city.diseaseCubes.get(disease) == 3) {
						continueOutbreak.add(city);
					}
				}
			}
		}
		return continueOutbreak;
	}

	public void continueRestOfOutbreaks(List<CityOLD> continueOutbreak) {
		for (int i = 0; i < continueOutbreak.size(); i++) {
			CityOLD city = continueOutbreak.get(i);
			performeOutbreak(city);
		}
	}

	public void performeOutbreak(CityOLD currentCity) {
		currentCity.isInOutbreak = true;
		moveOutbreakMarkForward();
		List<CityOLD> continueOutbreak = infectConnectedCities(currentCity);
		if (!continueOutbreak.isEmpty()) {
			continueRestOfOutbreaks(continueOutbreak);
		}
	}

}
