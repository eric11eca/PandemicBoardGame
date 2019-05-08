package cards;
import initialize.Board;

public class PlayerCard {	
	public Board.CardType cardType;
	public String cardName;
	public String color;
	
	public PlayerCard(Board.CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}
}
