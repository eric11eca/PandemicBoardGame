package Player;
import java.util.ArrayList;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public abstract class Player{

	public ArrayList<PlayerCard> hand = new ArrayList<>();
	public City location;
	public int action;

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
