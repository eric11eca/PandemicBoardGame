package Initialize;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Player.Player;
import javafx.util.Pair;

public class Board {
	public enum CardType{
		CITYCARD,
		EVENTCARD,
		EPIDEMIC;
	}
	
	
	public int playernumber = 0;
	public int initialhandcard = 0;
	public int epidemicCardNum = 0;
	int initialHandCard = 0;
	int outbreakCount = 0;
	int infectionRate;
	int yellowCubes = 24;
	int redCubes = 24;
	int blueCubes = 24;
	int blackCubes = 24;
	
	public boolean inQueitNight = false;
	
	public String cityWithGrant;
	
	public int idxofPlayerAirlift;
	public String nameofCityAirlift;
	
	public Map<String, String> infectionCardForecast = new HashMap<>();
	public List<Pair<String, Integer>> rearrangeInstruction = new ArrayList<>();
	
	public Set<String> curedDiseases = new HashSet<String>();
	public Map<String, City> cities = new HashMap<String, City>();
	public List<String> totalRoles = new ArrayList<String>();
	public List<Player> currentPlayers = new ArrayList<Player>();
	public List<PlayerCard> validPlayerCard = new ArrayList<PlayerCard>();
	public Map<String,PlayerCard> discardPlayerCard = new HashMap<>();
	public List<String> validInfectionCard = new ArrayList<String>();
	public List<String> discardInfectionCard = new ArrayList<String>();
	
	
}
