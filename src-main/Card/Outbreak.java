package Card;

import java.util.HashSet;

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
		HashSet<City> infectedNeighbors = new HashSet<>();
		for(City city : currentCity.neighbors) {
			int currentNum = city.diseaseCubes.get(disease);
			city.diseaseCubes.put(disease, currentNum+1);
			infectedNeighbors.add(city);
		}
		currentCity.neighbors = infectedNeighbors;
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
