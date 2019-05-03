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
	
	public enum Roles{
		SCIENTIST, MEDIC, 
		CONTINGENCYPLANNER, 
		DISPATCHER, 
		OPERATIONSEXPERT, 
		QUARANTINESPECIALIST, 
		RESEARCHER;
	}
	
	
	public Map<Board.Roles, Player> playerTable = new HashMap<>();
	
	public ListOfCityWithColorGenerator colorConcator = new ListOfCityWithColorGenerator();
	public String actionName;
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

	public Set<String> eradicatedDiseases = new HashSet<>();
	public Set<String> curedDiseases = new HashSet<>();
	public Map<String, City> cities = new HashMap<>();
	public List<Board.Roles> roleCardDeck = new ArrayList<>();
	public List<Player> currentPlayers = new ArrayList<>();
	public List<PlayerCard> validPlayerCard = new ArrayList<>();
	public Map<String, PlayerCard> discardPlayerCard = new HashMap<>();
	public List<String> validInfectionCard = new ArrayList<>();
	public List<String> discardInfectionCard = new ArrayList<>();

	public boolean gameEnd = false;
	public boolean playerLose = false;
	public boolean playerWin = false;
}