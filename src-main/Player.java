import java.util.ArrayList;

public abstract class Player {
	
	public ArrayList<PlayerCard> hand;


	public void receiveCard(PlayerCard playercard) {
		hand.add(playercard);
	}


	
}
