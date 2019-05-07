package cards;

import Initialize.Board;
import Player.Player;

public class Airlift implements EventCard{
	private Board board;
	
	public Airlift(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		int playeridx = board.idxofPlayerAirlift;
		Player player = board.currentPlayers.get(playeridx); 
		String cityName = board.nameofCityAirlift;
		player.playerData.location = board.cities.get(cityName);
	}
}
