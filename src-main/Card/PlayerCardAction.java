package Card;

import Initialize.Board;

public class PlayerCardAction {
	Board board;
	PlayerCard playerCard;
	
	private AirliftEvent airliftEvent;
	private ForecastEvent forecastEvent;
	private OneQueitNightEvent nightEvent;
	private GovernmentGrantEvent grantEvent;
	
	private final String airlift = "Airlift";
	private final String forecast = "Forecast";
	private final String oneQuietNight = "OneQuietNight";
	private final String governmentGrant = "GovernmentGrant";
	private final String resilientPopulation = "ResilientPopulation";
	
	public PlayerCardAction(Board gameBoard, PlayerCard card) {
		board = gameBoard;
		playerCard = card;
		airliftEvent = new AirliftEvent(board);
		forecastEvent = new ForecastEvent(board);
		nightEvent = new OneQueitNightEvent(board);
		grantEvent = new GovernmentGrantEvent(board);
	}
	
	public boolean excuteEventCard() {
		if (playerCard.cardName == airlift) {
			airliftEvent.airlift();
			return true;
		} else if (playerCard.cardName == forecast) {
			forecastEvent.forecast();
			return true;
		} else if (playerCard.cardName == oneQuietNight) {
			nightEvent.skipNextInfection();
			return true;
		} else if (playerCard.cardName == governmentGrant) {
			grantEvent.addResearchStation();
			return true;
		} else if (playerCard.cardName.equals(resilientPopulation)) {
			return true;
		}
		return false;
	}
	
	public boolean excuteCityCard() {
		return false;
		
	}
	
	public boolean excuteCard() {
		if (playerCard.cardType.equals(Board.CardType.EVENTCARD)) {
			return excuteEventCard();
		} else if (playerCard.cardType.equals(Board.CardType.CITYCARD)) {
			return excuteCityCard();
		}
		return false;
	}
}
