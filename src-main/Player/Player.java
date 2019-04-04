package Player;

import java.util.HashMap;
import java.util.Map;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public abstract class Player{

	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public int action = 4;
	public PlayerCard specialEventCard;
	public String cardToBeDiscard;
	public boolean handOverFlow = false;
	public Board board;
	
	
	public Player(Board gameBoard){
		board=gameBoard;
	}

	public void receiveCard(PlayerCard playerCard) {
		if(hand.size() >= 7){
			handOverFlow = true;
			discardCard(cardToBeDiscard);
		} else {
			hand.put(playerCard.cardName, playerCard);     
		}
	}
	
	public boolean useCard(String cardName) {
		boolean cardUsed = false;
		if(cardName.equals(specialEventCard.cardName)) {
			cardUsed = specialEventCard.excuteEvents();
			if(cardUsed) {
				this.specialEventCard = null;
			}
		} else {
			PlayerCard card = hand.get(cardName);
			cardUsed = card.excuteEvents();
		}
		return cardUsed;
	}

	public boolean discardCard(String cardName){
		if(hand.containsKey(cardName)){
			hand.remove(cardName);
			return true;
		}
		return false;
	}

	public void move(City city) {
		if(location.neighbors.contains(city)){
			location = city;
		}
	}

	
	public abstract void discoverCure(DiscoverCure discoverCure);
	
}
