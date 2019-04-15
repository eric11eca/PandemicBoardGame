package Card;

import java.util.HashSet;
import java.util.Set;

import Initialize.Board;
import Initialize.City;

public class Outbreak {
	Board board;
	
	public Outbreak(Board gameBoard) {
		board = gameBoard;
	}

	public void moveOutbreakMarkForward() {
		board.outbreakMark += 1;
		if(board.outbreakMark == 8) {
			board.gameEnd = true;
			board.playerLose = true;
		}
	}
	
	public void infectConnectedCities(City currentCity) {
		String disease = currentCity.color;
		HashSet<City> infectedNeighbors = new HashSet<>();
		for(City city : currentCity.neighbors) {
			int currentNum = city.diseaseCubes.get(disease);
			city.diseaseCubes.put(disease, currentNum+1);
			infectedNeighbors.add(city);
		}
		currentCity.neighbors = infectedNeighbors;
	}
	
}
