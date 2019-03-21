package Player;
import java.util.ArrayList;

import Card.PlayerCard;
import Initialize.City;

public abstract class Player{
//	private int handCapacity;
	
	
//    public Player(int playerNumber){
//		if (playerNumber == 4) {
//			this.handCapacity = 2;
//		} else if (playerNumber == 3) {
//			this.handCapacity = 3;
//		} else if (playerNumber == 2) {
//			this.handCapacity = 4;
//		}
//		//this.hand = new ArrayList<Card>;
//	}

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
//	public int getHandCapacity() {
//		return this.handCapacity;	
//	}

	public void move(City city) {
		if(location.neighbors.contains(city)){
			location = city;
		}
	}
	
	
	
	
	
	
}