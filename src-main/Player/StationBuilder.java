package Player;

import java.util.Collection;

import Initialize.Board;
import Initialize.City;

public abstract class StationBuilder {
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
	
	public abstract void buildStation();
}
