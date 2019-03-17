import java.util.ArrayList;

public class Player {
	
	int playerNumber;
	ArrayList<Card> hand;
	
	public Player(int playerNumber){
		this.playerNumber=playerNumber;
		this.hand = new ArrayList<Card>;
	}

	public int getPlayerNumber() {
		return this.playerNumber;
	}

	public Card[] getHand() {
		// TODO Auto-generated method stub
		return this.hand();
	}

}
