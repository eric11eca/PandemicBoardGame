package playerAction;

import data.Board;
import game.City;
import game.player.Player;

public class DirectFlight extends PlayerAction{
	public DirectFlight(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		if (player.cityCard.cardType == Board.CardType.CITYCARD) {
			board.cardToBeDiscard.add(player.cityCard.cardName);
			player.discardCard();
			player.consumeAction();
			City destination = board.cities.get(player.cityCard.cardName);
			moveTo(destination);
		}
		return true;
	}
}
