package cards;

import data.Board;

public class EventCardFactory {
	Board board;
	
	public EventCardFactory(Board board) {
		this.board = board;
	}
	
	public EventCard createEventCard(String cardName) {
		EventCard card = null;
		if(cardName.equals("Airlift")) {
			card = new Airlift(board);
		} else if (cardName.equals("Forecast")) {
			card = new ForecastEvent(board);
		} else if (cardName.equals("GovernmentGrant")) {
			card = new GovernmentGrant(board);
 		} else if (cardName.equals("OneQuietNight")) {
 			card = new OneQuietNight(board);
 		} else if (cardName.equals("ResilientPopulation")) {
 			card = new ResilientPopulation(board);
 		}
		return card;
	}
}
