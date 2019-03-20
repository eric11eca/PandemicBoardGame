import java.util.ArrayList;

public abstract class Player{
	
	public ArrayList<PlayerCard> hand;
	public City location;

	public void receiveCard(PlayerCard playercard) {
		if(hand.size() >= 7){
			// somehow discard card
		}
		hand.add(playercard);
	}

	public boolean discardCard(PlayerCard playercard){
		if(hand.contains(playercard)){
			hand.remove(playercard);
			return true;
		}
		return false;
	}

	public void move(City city) {
		if(location.getConnections().contains(city)){
			location = city;
		}
	}
	
	
	
	
	
	
}
