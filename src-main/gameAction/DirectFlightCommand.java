package gameAction;

import cards.PlayerCard;
import data.Board;
import player.Player;

public class DirectFlightCommand implements Command{
	private Board board;
	private Player player;

	public DirectFlightCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		String cardName = board.cityCardNameDirect;
		PlayerCard cityCard = player.playerData.hand.get(cardName);
		player.directFlight(cityCard);
		board.doesChangeLocation = true;
	}
}