package Card;

import java.util.HashMap;
import java.util.Map;

import Initialize.Board;

public class EventCardAction {
	Board board;
	PlayerCard playerCard;
	
	private Map<String, EventCard> eventCards = new HashMap<>();
	public AirliftEvent airliftEvent;
	public ForecastEvent forecastEvent;
	public OneQuietNightEvent nightEvent;
	public GovernmentGrantEvent grantEvent;
	public ResilientPopulationEvent resilientEvent;

	public EventCardAction(Board gameBoard) {
		board = gameBoard;
		airliftEvent = new AirliftEvent(board);
		eventCards.put("Airlift", airliftEvent);
		forecastEvent = new ForecastEvent(board);
		eventCards.put("Forecast", forecastEvent);
		nightEvent = new OneQuietNightEvent(board);
		eventCards.put("OnQuietNight", nightEvent);
		grantEvent = new GovernmentGrantEvent(board);
		eventCards.put("GovernmentGrant", grantEvent);
		resilientEvent = new ResilientPopulationEvent(board);
		eventCards.put("ResilientPopulation", resilientEvent);
	}

	public boolean executeEventCard(String eventName) {
		if(eventCards.containsKey(eventName)) {
			eventCards.get(eventName).executeEvent();
			return true;
		}
		return false;
	}
}
