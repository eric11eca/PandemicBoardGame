package gameAction;

import data.Board;
import player.Player;

public class PlayEventCardCommand implements Command{
	private Board board;
	private Player player;

	public PlayEventCardCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.useEventCard(board.eventCardName);
		if(board.eventCardName.equals(board.messages.getString("Airlift"))) {
			board.doesChangeLocation = true;
		}
	}
}
