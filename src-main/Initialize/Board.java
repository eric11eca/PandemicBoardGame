package Initialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import Card.PlayerCard;
import Parse.ListOfCityWithColorGenerator;
import Player.Player;
import javafx.util.Pair;

public class Board {
	public enum CardType {
		CITYCARD, EVENTCARD, EPIDEMIC;
	}
	
	public enum ActionName {
		DIRECTFLIGHT, PLAYEVENTCARD, CUREDISEASE, TREATDISEASE, DRIVE,
		CHARTERFLIGHT, SHUTTLEFLIGHT, BUILDRESEARCH, SHAREKNOWLEDGE;
	}
	
	public ListOfCityWithColorGenerator colorConcator = new ListOfCityWithColorGenerator();
	
	public ActionName actionName;
	public int playernumber = 0;
	public int initialhandcard = 0;
	public int epidemicCardNum = 0;
	public int outbreakMark = 0;
	public Map<String, City> currentResearchStation = new HashMap<>();
	public Stack<Integer> infectionRateTracker = new Stack<>();
	public Map<String, Integer> remainDiseaseCube = new HashMap<>();
	
	public String eventCardName;
	public String cityCardNameDirect;
	public String cityCardNameCharter;
	public String diseaseBeingTreated;
	public String driveDestinationName;
	public String shuttleDestinationName;
	public Object stationToRemove;
	public Player playerToShare;
	public PlayerCard cityToShare;
	public boolean isGiving;
	public List<String> cardToBeDiscard = new ArrayList<>();
	public List<PlayerCard> cardsToCureDisease = new ArrayList<>();
	
	public Player currentPlayer;
	public int currentPlayerIndex = 0;
	
	public int idxofPlayerAirlift;
	public boolean inQueitNight = false;
	public String cityWithGrant;
	public String nameofCityAirlift;
	public String cardRemovedByResilient;
	public Map<String, String> infectionCardForecast = new HashMap<>();
	public List<Pair<String, Integer>> rearrangeInstruction = new ArrayList<>();

	public Set<String> eradicatedDiseases = new HashSet<String>();
	public Set<String> curedDiseases = new HashSet<String>();
	public Map<String, City> cities = new HashMap<String, City>();
	public List<String> roleCardDeck = new ArrayList<String>();
	public List<Player> currentPlayers = new ArrayList<Player>();
	public List<PlayerCard> validPlayerCard = new ArrayList<PlayerCard>();
	public Map<String, PlayerCard> discardPlayerCard = new HashMap<>();
	public List<String> validInfectionCard = new ArrayList<String>();
	public List<String> discardInfectionCard = new ArrayList<String>();

	public boolean gameEnd = false;
	public boolean playerLose = false;
	public boolean playerWin = false;
}