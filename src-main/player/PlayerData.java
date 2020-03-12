package player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cards.PlayerCard;
import data.Board;
import data.City;

public class PlayerData {
	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public Board.Roles role;
	public int action;
	public String specialEventCard; 
	
	public Iterator<String> cityIterator() {
		return hand.keySet().iterator();
	}
}