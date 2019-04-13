package Card;

import Initialize.Board;
import Player.Player;

public class AirliftEvent {
	private Board board;
	
	public AirliftEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public boolean airlift() {
		int playeridx = board.idxofPlayerAirlift;
		Player player = board.currentPlayers.get(playeridx);
		String cityName = board.nameofCityAirlift;
		player.location = board.cities.get(cityName);
		return true;
	}
}
