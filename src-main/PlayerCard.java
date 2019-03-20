
public abstract class PlayerCard {
	Board.CardType cardType;
	String cardName;
	
	public PlayerCard(Board.CardType cardType, String cardName) {
		this.cardType = cardType;
		this.cardName = cardName;
	}
}
