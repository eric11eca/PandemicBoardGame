package Card;

import java.util.HashMap;
import java.util.Map;

import Initialize.Board;
import Initialize.City;

public class Outbreak {
	Board board;
	
	public Outbreak(Board gameBoard) {
		board = gameBoard;
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
					performeOutbreak(city);
				} else {
					city.diseaseCubes.put(disease, currentNum+1);
					if(city.diseaseCubes.get(disease) == 3) {
						performeOutbreak(city);
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
	
}
