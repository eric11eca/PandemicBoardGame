package playerAction;

import data.Board;
import data.City;
import player.Player;

public class CharterFlight extends PlayerAction {
	public CharterFlight(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		City destination = board.cityCardNameCharter;
		board.cardToBeDiscard.add(player.playerData.location.cityName);
		player.discardCard();
		moveTo(destination);
		player.consumeAction();
		return true;
	}
}
