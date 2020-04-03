package cardActions;

import data.Board;
import game.cards.event.CardEvent;

public class EventCardAction {
	Board board;

	public EventCardAction(Board gameBoard) {
		board = gameBoard;
	}

	public boolean executeEventCard(String eventName) {
		if(board.eventCards.containsKey(eventName)) {
			CardEvent eventCard = board.eventCards.get(eventName);
			eventCard.executeEvent();
			return true;
		}
		return false;
	}
}
