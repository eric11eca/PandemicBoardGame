package Player;

import Initialize.Board;
import Initialize.City;

public class StationBuilder {
	Player player;
	Board board;
	public StationBuilder(Player actualPlayer, Board gameBoard) {
		player = actualPlayer;
		board = gameBoard;
	}
	
	public void buildStation() {
		if(player.location.researchStation) {
			throw new RuntimeException("NoCityCardException");
		}
		City playerLocation = player.location;
		playerLocation.researchStation = true;
		board.currentResearchStation.put(playerLocation.cityName, playerLocation);
		player.consumeAction();
	}
	
	public void removeStation(City stationToRemove) {
		board.currentResearchStation.remove(stationToRemove.cityName);
		stationToRemove.researchStation = false;
	}
}
