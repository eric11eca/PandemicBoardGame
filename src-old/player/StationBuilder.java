package player;

import data.Board;
import game.City;

public class StationBuilder {
	PlayerData playerData;
	Board board;

	public StationBuilder(PlayerData actualPlayer, Board gameBoard) {
		playerData = actualPlayer;
		board = gameBoard;
	}

	public void buildStation() {
		if (playerData.location.hasResearchStation()) {
			throw new RuntimeException("ResearchStationBuilt");
		}
		City playerLocation = playerData.location;
		playerLocation.buildResearchStation();
		board.currentResearchStation.put(playerLocation.getName(), playerLocation);
	}

	public void removeStation(City stationToRemove) {
		board.currentResearchStation.remove(stationToRemove.getName());
		stationToRemove.removeResearchStation();
	}
}
