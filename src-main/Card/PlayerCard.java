package Card;
import Initialize.Board;

public class PlayerCard {	
	private final String airlift = "Airlift";
	private final String forecast = "Forecast";
	private final String oneQuietNight = "OneQuietNight";
	private final String governmentGrant = "GovernmentGrant";
	private final String resilientPopulation = "ResilientPopulation";
	
	public Board.CardType cardType;
	public String cardName;
	public String color;
	
	public PlayerCard(Board.CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}
	
	public boolean excuteEvents() {
		if (this.cardType == Board.CardType.EVENTCARD) {
			if (this.cardName == airlift) {
				return true;
			} else if (this.cardName == forecast) {
				return true;
			} else if (this.cardName == oneQuietNight) {
				return true;
			} else if (this.cardName == governmentGrant) {
				return true;
			} else if (this.cardName == resilientPopulation) {
				return true;
			}
		} 
		return false;
	}
}
