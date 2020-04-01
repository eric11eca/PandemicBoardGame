package player;

import data.Board;
import data.CityOLD;

public class StationBuilder {
	PlayerData playerData;
	Board board;

	public StationBuilder(PlayerData actualPlayer, Board gameBoard) {
		playerData = actualPlayer;
		board = gameBoard;
	}

	public void buildStation() {
		if (playerData.location.researchStation) {
			throw new RuntimeException("ResearchStationBuilt");
		}
		CityOLD playerLocation = playerData.location;
		playerLocation.researchStation = true;
		board.currentResearchStation.put(playerLocation.getName(), playerLocation);
	}

	public void removeStation(CityOLD stationToRemove) {
		board.currentResearchStation.remove(stationToRemove.getName());
		stationToRemove.researchStation = false;
	}
}
