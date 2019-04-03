package Initialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Player.Player;

public class Board {
	public enum CardType{
		CITYCARD,
		EVENTCARD,
		EPIDEMIC;
	}
	
	public int playernumber = 0;
	public int initialhandcard = 0; 
	int outbreakCount = 0;
	int infectionRate;
	int yellowCubes = 24;
	int redCubes = 24;
	int blueCubes = 24;
	int blackCubes = 24;
	
	boolean handOverFlow = false;
	
	public Set<String> curedDiseases = new HashSet<>();
	public Map<String, City> cities = new HashMap<>();
	public List<String> totalRoles = new ArrayList<>();
	public List<Player> currentPlayers = new ArrayList<>();
	public List<PlayerCard> validPlayerCard = new ArrayList<>();
	public Map<String,PlayerCard> discardPlayerCard = new HashMap<>();
	public List<String> validInfectionCard = new ArrayList<>();
	public List<String> discardInfectionCard = new ArrayList<>();
}
