package Card;

import Initialize.Board;

public class EventCardAction {
	Board board;
	PlayerCard playerCard;

	public AirliftEvent airliftEvent;
	public ForecastEvent forecastEvent;
	public OneQueitNightEvent nightEvent;
	public GovernmentGrantEvent grantEvent;
	public ResilientPopulationEvent resilientEvent;

	private final String airlift = "Airlift";
	private final String forecast = "Forecast";
	private final String oneQuietNight = "OneQuietNight";
	private final String governmentGrant = "GovernmentGrant";
	private final String resilientPopulation = "ResilientPopulation";

	public EventCardAction(Board gameBoard, PlayerCard card) {
		board = gameBoard;
		playerCard = card;
		airliftEvent = new AirliftEvent(board);
		forecastEvent = new ForecastEvent(board);
		nightEvent = new OneQueitNightEvent(board);
		grantEvent = new GovernmentGrantEvent(board);
		resilientEvent = new ResilientPopulationEvent(board);
	}

	public boolean excuteEventCard() {
		if (playerCard.cardName.equals(airlift)) {
			return airliftEvent.airlift();
		} else if (playerCard.cardName.equals(forecast)) {
			return forecastEvent.forecast();
		} else if (playerCard.cardName.equals(oneQuietNight)) {
			return nightEvent.skipNextInfection();
		} else if (playerCard.cardName.equals(governmentGrant)) {
			return grantEvent.addResearchStation();
		} else if (playerCard.cardName.equals(resilientPopulation)) {
			return resilientEvent.resilientPopulation();
		}
		return false;
	}
}
