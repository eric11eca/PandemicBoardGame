package Player;
import java.util.ArrayList;

import Card.PlayerCard;
import Initialize.City;

public abstract class Player{

	public ArrayList<PlayerCard> hand = new ArrayList<>();
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
		if(location.neighbors.contains(city)){
			location = city;
		}
	}
	
	
	
	
	
	
}
