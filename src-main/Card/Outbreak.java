package Card;

import java.util.ArrayList;
import java.util.List;

import Initialize.Board;
import Initialize.City;

public class Outbreak {
	Board board;
	public List<City> continueOutbreak;
	
	public Outbreak(Board gameBoard) {
		board = gameBoard;
		continueOutbreak = new ArrayList<>();
	}

	public boolean moveOutbreakMarkForward() {
		board.outbreakMark += 1;
		if(board.outbreakMark == 8) {
			board.gameEnd = true;
			board.playerLose = true;
		}
		return true;
	}
	
	public boolean infectConnectedCities(City currentCity) {
		String disease = currentCity.color;
		for(String cityName : currentCity.neighbors.keySet()) {
			City city = currentCity.neighbors.get(cityName);
			if(!city.isInOutbreak) {
				int currentNum = city.diseaseCubes.get(disease);
				if(currentNum >= 3) {
					continueOutbreak.add(city);
				} else {
					city.diseaseCubes.put(disease, currentNum+1);
					if(city.diseaseCubes.get(disease) == 3) {
						continueOutbreak.add(city);
					}
				}
			}
		}
		return true;
	}
	
	public boolean performeOutbreak(City currentCity) {
		boolean outbreak = false;
		currentCity.isInOutbreak = true;
		outbreak = moveOutbreakMarkForward();
		outbreak = infectConnectedCities(currentCity);
		return outbreak; 
	}
	
	public boolean continueRestOfOutbreaks() {
		boolean allOutbreakFinished = false;
		for(City city : continueOutbreak) {
			allOutbreakFinished = performeOutbreak(city);
		}
		return allOutbreakFinished;
	}
	
}
