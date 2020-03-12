package player;

import data.Board;
import data.City;

public class StationBuilder {
	PlayerData playerData;
	Board board;
	
	public StationBuilder(PlayerData actualPlayer, Board gameBoard) {
		playerData = actualPlayer;
		board = gameBoard;
	}
	
	public void buildStation() {
		if(playerData.location.researchStation) {
			throw new RuntimeException("ResearchStationBuilt");
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
