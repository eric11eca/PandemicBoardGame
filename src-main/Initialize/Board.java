package Initialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import Card.PlayerCard;
import Player.Player;
import javafx.util.Pair;

public class Board {
	public enum CardType {
		CITYCARD, EVENTCARD, EPIDEMIC;
	}

	public String nameofCardBeingPlayed;

	public int playernumber = 0;
	public int initialhandcard = 0;
	public int epidemicCardNum = 0;
	int initialHandCard = 0;
	public Stack<Integer> infectionRateTrack = new Stack<>();
	public int outbreakMark = 0;
	public Map<String, Integer> remainDiseaseCube = new HashMap<>();
	
	public String cityWithGrant;
	public int idxofPlayerAirlift;
	public String nameofCityAirlift;
	public boolean inQueitNight = false;
	public String cardRemovedByResilient;
	public Map<String, String> infectionCardForecast = new HashMap<>();
	public List<Pair<String, Integer>> rearrangeInstruction = new ArrayList<>();

	public Set<String> eradicatedDiseases = new HashSet<String>();
	public Set<String> curedDiseases = new HashSet<String>();
	public Map<String, City> cities = new HashMap<String, City>();
	public List<String> totalRoles = new ArrayList<String>();
	public List<Player> currentPlayers = new ArrayList<Player>();
	public List<PlayerCard> validPlayerCard = new ArrayList<PlayerCard>();
	public Map<String, PlayerCard> discardPlayerCard = new HashMap<>();
	public List<String> validInfectionCard = new ArrayList<String>();
	public List<String> discardInfectionCard = new ArrayList<String>();

	public boolean gameEnd = false;
	public boolean playerLose = false;
	public boolean playerWin = false;

	

	

	

}
