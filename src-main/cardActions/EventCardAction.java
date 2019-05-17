package cardActions;

import cards.EventCard;
import cards.PlayerCard;
import data.Board;

public class EventCardAction {
	Board board;

	public EventCardAction(Board gameBoard) {
		board = gameBoard;
	}

	public boolean executeEventCard(String eventName) {
		if(board.eventCards.containsKey(eventName)) {
			EventCard eventCard = board.eventCards.get(eventName);
			eventCard.executeEvent();
			return true;
		}
		return false;
	}
}
