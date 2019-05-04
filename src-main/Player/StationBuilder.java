package Player;

import Initialize.Board;
import Initialize.City;

public class StationBuilder {
	PlayerData playerData;
	Board board;
	Player player;
	public StationBuilder(PlayerData actualPlayer, Board gameBoard) {
		playerData = actualPlayer;
		board = gameBoard;
		player = new Player(board, playerData);
	}
	
	public void buildStation() {
		if(playerData.location.researchStation) {
			throw new RuntimeException("ResearchStationBuilt");
		}
		City playerLocation = playerData.location;
		playerLocation.researchStation = true;
		board.currentResearchStation.put(playerLocation.cityName, playerLocation);
		player.consumeAction();
	}
	
	public void removeStation(City stationToRemove) {
		board.currentResearchStation.remove(stationToRemove.cityName);
		stationToRemove.researchStation = false;
	}
}
