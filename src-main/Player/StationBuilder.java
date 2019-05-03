package Player;

import Initialize.Board;
import Initialize.City;

public class StationBuilder {
	PlayerData playerData;
	Board board;
	
	public StationBuilder(PlayerData actualPlayer, Board gameBoard) {
		playerData = actualPlayer;
		board = gameBoard;
	}
	
	public void buildStation() {
		if(playerData.location.researchStation) {
			throw new RuntimeException("This location already has research station.");
		}
		City playerLocation = playerData.location;
		playerLocation.researchStation = true;
		board.currentResearchStation.put(playerLocation.cityName, playerLocation);
	}
	
	public void removeStation(City stationToRemove) {
		board.currentResearchStation.remove(stationToRemove.cityName);
		stationToRemove.researchStation = false;
	}
}
