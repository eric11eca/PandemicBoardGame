package gameAction;

import cardActions.EpidemicCardAction;
import cards.PlayerCard;
import data.Board;

public class DrawTwoPlayerCardsCommand implements Command {
	Board board;
	private EpidemicCardAction epidemic;
	public boolean doesChangeLocation = false;

	public DrawTwoPlayerCardsCommand(EpidemicCardAction epidemicAction) {
		board = Board.getInstance();
		epidemic = epidemicAction;
	}

	@Override
	public void execute() {
		for (int i = 0; i < 2; i++) {
			PlayerCard playerCard = null;	
			if(board.validPlayerCards.isEmpty()) {	
				throw new RuntimeException("NoPlayerCardsException");
			} else {
				playerCard = board.validPlayerCards.get(0);
			}
			if (playerCard.cardType == Board.CardType.EPIDEMIC) {
				epidemic.performEpidemic();
			} else {
				board.currentPlayer.receiveCard(playerCard);
			}
			board.validPlayerCards.remove(0);
		}
		if (board.currentPlayer.playerData.hand.size() > 7) {
			throw new RuntimeException("PlayerHandOverflow");
		}
	}
}
