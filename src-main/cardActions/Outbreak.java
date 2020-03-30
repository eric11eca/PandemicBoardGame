package cardActions;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.City;

public class Outbreak {
	Board board;
	
	public Outbreak(Board gameBoard) {
		board = gameBoard;
	}
	
	public void moveOutbreakMarkForward() {
		board.outbreakMark += 1;
		if(board.outbreakMark == 8) {
			throw new RuntimeException("OutbreakException");
		}
	}
	
	public List<City> infectConnectedCities(City currentCity) {
		String disease = currentCity.color;
		List<City> continueOutbreak = new ArrayList<>();
		for(String cityName : currentCity.neighbors.keySet()) {
			City city = currentCity.neighbors.get(cityName);
			if(!city.isInOutbreak) {
				int currentNum = city.diseaseCubes.get(disease);
				if(currentNum >= 3) {
					continueOutbreak.add(city);
				} else {
					if(board.remainDiseaseCube.get(disease) == 0) {
						String errorMessage = MessageFormat.format("OutOf{0}", disease);
						throw new RuntimeException(errorMessage);
					}
					city.diseaseCubes.put(disease, currentNum+1);
					int remainingCube = board.remainDiseaseCube.get(disease);
					board.remainDiseaseCube.put(disease, remainingCube - 1);
					if(city.diseaseCubes.get(disease) == 3) {
						continueOutbreak.add(city);
					}
				}
			}
		}
		return continueOutbreak;
	}
	
	public void continueRestOfOutbreaks(List<City> continueOutbreak) {
		for(int i = 0; i < continueOutbreak.size(); i++) {
			City city = continueOutbreak.get(i);
			performeOutbreak(city);
		}
	}
	
	public void performeOutbreak(City currentCity) {
		currentCity.isInOutbreak = true;
		moveOutbreakMarkForward();
		List<City> continueOutbreak = infectConnectedCities(currentCity);
		if(!continueOutbreak.isEmpty()) {
			continueRestOfOutbreaks(continueOutbreak);
		}
	}
	

	
}
