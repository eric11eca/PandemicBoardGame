package Card;
import Initialize.Board;

public class PlayerCard {
	public Board.CardType cardType;
	public String cardName;
	
	public PlayerCard(Board.CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}
}