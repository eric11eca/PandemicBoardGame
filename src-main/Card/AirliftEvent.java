package Card;

import Initialize.Board;
import Player.Player;

public class AirliftEvent implements EventCard{
	private Board board;
	
	public AirliftEvent(Board gameBoard) {
		board = gameBoard;
	}

	@Override
	public void executeEvent() {
		int playeridx = board.idxofPlayerAirlift;
		Player player = board.currentPlayers.get(playeridx);
		String cityName = board.nameofCityAirlift;
		player.location = board.cities.get(cityName);
	}
}
