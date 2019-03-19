import java.util.ArrayList;

public abstract class Player {
	
	public ArrayList<PlayerCard> hand;


	public void receiveCard(PlayerCard playercard) {
		hand.add(playercard);
	}


	public boolean discardCard(String cardname){
		for(int i = 0; i < hand.size();i++){
			if(hand.get(i).getName().equals(cardname)){
				hand.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean handContains(String cardname){
		for(PlayerCard playercard : hand){
			if(playercard.getName().equals("Beijing")){
				return true;
			}
		}
		return false;
	}
	

	
}
