package Player;

import java.util.Collection;

import Initialize.Board;
import Initialize.City;

public class StationBuilder {
	Player player;
	Board board;
	public StationBuilder(Player actualPlayer, Board gameBoard) {
		player = actualPlayer;
		board = gameBoard;
	}
	
	public City returnRandomResearchStationCity() {
		Collection<City> cities = board.currentResearchStation.values();
		int randomInt = player.random.nextInt(6);
		City city = (cities.toArray(new City[0]))[randomInt];
		return city;
	}
	
	public void buildStation() {
		if(player.location.researchStation) {
			throw new RuntimeException("This location already has research station.");
		}
		City playerLocation = player.location;
		if(board.currentResearchStation.size() == 6) {
			City randomCity = player.buildStationModel.returnRandomResearchStationCity();
			board.currentResearchStation.remove(randomCity.cityName);
			randomCity.researchStation = false;
		}
		playerLocation.researchStation = true;
		board.currentResearchStation.put(playerLocation.cityName, playerLocation);
		player.consumeAction();
	}
}
