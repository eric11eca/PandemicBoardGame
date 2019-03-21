package Initialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Card.PlayerCard;
import Player.Player;

public class Board {
	public enum CardType{
		CITYCARD,
		EVENTCARD,
		EPIDEMIC;
	}
	
	int outbreakCount = 0;
	int infectionRate;
	int yellowCubes = 24;
	int redCubes = 24;
	int blueCubes = 24;
	int blackCubes = 24;
	
	//int difficulty = 0;
	
	boolean yellowCure = false;
	boolean redCure = false;
	boolean blueCure = false;
	boolean blackCure = false;
	
	public Map<String, City> cities = new HashMap<String, City>();
	
	List<Player> currentPlayers = new ArrayList<Player>();
	public List<PlayerCard> validPlayerCard = new ArrayList<PlayerCard>();
	List<PlayerCard> discardPlayerCard = new ArrayList<PlayerCard>();
	public List<String> validInfectionCard = new ArrayList<String>();
	List<String> discardInfectionCard = new ArrayList<String>();
}