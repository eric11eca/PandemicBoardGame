package gameAction;

import data.Board;
import player.Player;

public class ShareKnowledgeCommand implements Command{
	private Board board;
	private Player player;

	public ShareKnowledgeCommand() {
		board = Board.getInstance();
	}

	@Override
	public void execute() {
		player = board.currentPlayer;
		player.shareKnowledge();
	}
}
