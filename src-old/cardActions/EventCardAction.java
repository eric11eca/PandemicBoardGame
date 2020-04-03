package cardActions;

import data.Board;
import game.event.Event;

public class EventCardAction {
	Board board;

	public EventCardAction(Board gameBoard) {
		board = gameBoard;
	}

	public boolean executeEventCard(String eventName) {
		if(board.eventCards.containsKey(eventName)) {
			Event eventCard = board.eventCards.get(eventName);
			eventCard.executeEvent();
			return true;
		}
		return false;
	}
}
