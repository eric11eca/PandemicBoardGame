package cardActions;

import java.util.ArrayList;
import java.util.List;

import initialize.Board;
import initialize.City;

public class Outbreak {
	Board board;
	
	public Outbreak(Board gameBoard) {
		board = gameBoard;
	}
	
	public void moveOutbreakMarkForward() {
		board.outbreakMark += 1;
		if(board.outbreakMark == 8) {
//			board.gameEnd = true;
//			board.playerLose = true;
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
//						board.gameEnd = true;
//						board.playerLose = true;
//						return continueOutbreak;
						throw new RuntimeException("OutOf"+disease);
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
//			if(board.gameEnd) {
//				return;
//			}
		}
	}
	
	public void performeOutbreak(City currentCity) {
		currentCity.isInOutbreak = true;
		moveOutbreakMarkForward();
//		if(board.gameEnd) {
//			return;
//		}
		List<City> continueOutbreak = infectConnectedCities(currentCity);
//		if(board.gameEnd) {
//			return;
//		}
		if(!continueOutbreak.isEmpty()) {
			continueRestOfOutbreaks(continueOutbreak);
		}
	}
	

	
}
