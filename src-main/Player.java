import java.util.ArrayList;

public class Player {
	private int handCapacity;
	//ArrayList<Card> hand;
	
	public Player(int playerNumber){
		if (playerNumber == 4) {
			this.handCapacity = 2;
		} else if (playerNumber == 3) {
			this.handCapacity = 3;
		} else if (playerNumber == 2) {
			this.handCapacity = 4;
		}
		//this.hand = new ArrayList<Card>;
	}

	public int getHandCapacity() {
		return this.handCapacity;
	}

	/*public Card[] getHand() {
		return this.hand();
	}*/

}
