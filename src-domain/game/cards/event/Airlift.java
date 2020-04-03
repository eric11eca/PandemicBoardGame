package game.cards.event;

import data.Board;
import game.player.Player;

public class Airlift implements CardEvent {
	/*
	 * Move any pawn to any city. Get permission before moving other player's pawn.
	 */
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
