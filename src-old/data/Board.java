package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cardActions.EventCardAction;
import cards.EventCard;
import cards.PlayerCard;
import initialize.Messages;
import parse.ListOfCityWithColorGenerator;
import player.Player;

public class Board {
	public Board() {
	}

	public enum CardType {
		CITYCARD, EVENTCARD, EPIDEMIC;
	}

	public enum ActionName {
		DIRECTFLIGHT, PLAYEVENTCARD, CUREDISEASE, TREATDISEASE, DRIVE, CHARTERFLIGHT, SHUTTLEFLIGHT, BUILDSTATION,
		SHAREKNOWLEDGE;
	}

	public enum Roles {
		SCIENTIST, MEDIC, CONTINGENCYPLANNER, DISPATCHER, OPERATIONSEXPERT, QUARANTINESPECIALIST, RESEARCHER;
	}

	public Map<String, String> messagesToShow = new HashMap<>();

	public Map<Board.Roles, Player> playerTable = new HashMap<>();

	public ListOfCityWithColorGenerator colorConcator = new ListOfCityWithColorGenerator();
	@Deprecated // Refactor into method parameter
	public ActionName actionName;
	public int playernumber = 0;
	public int initialhandcard = 0;
	public int epidemicCardNum = 0;
	public int outbreakMark = 0;
	public Map<String, CityOLD> currentResearchStation = new HashMap<>();
	public Stack<Integer> infectionRateTracker = new Stack<>();
	public Map<String, Integer> remainDiseaseCube = new HashMap<>();
	public Set<String> eradicatedColor = new HashSet<>();
	@Deprecated // Refactor as parameter
	public EventCardAction eventCardAction;

	@Deprecated // Refactor as parameter
	public String eventCardName;
	@Deprecated // Refactor as parameter
	public String cityCardNameDirect;
	@Deprecated // Refactor as parameter
	public CityOLD cityCardNameCharter;
	@Deprecated // Refactor as parameter
	public String diseaseBeingTreated;
	@Deprecated // Refactor as parameter
	public CityOLD driveDestination;
	@Deprecated // Refactor as parameter
	public String shuttleDestinationName;
	@Deprecated // Refactor as parameter
	public Object stationToRemove;
	@Deprecated // Refactor as parameter
	public Player playerToShare;
	@Deprecated // Refactor as parameter
	public PlayerCard cityToShare;
	public Map<String, EventCard> eventCards = new HashMap<>();
	@Deprecated // Refactor as parameter
	public boolean isGiving;
	@Deprecated // Refactor as parameter
	public List<String> cardToBeDiscard = new ArrayList<>();
	@Deprecated // Refactor as parameter
	public List<PlayerCard> cardsToCureDisease = new ArrayList<>();

	public Player currentPlayer;
	public int currentPlayerIndex = 0;
	@Deprecated // Refactor as parameter
	public int idxofPlayerAirlift;
	public boolean inQueitNight = false;
	@Deprecated // Refactor as parameter
	public String cityWithGrant;
	@Deprecated // Refactor as parameter
	public String nameofCityAirlift;
	@Deprecated // Refactor as parameter
	public String cardRemovedByResilient;
	public List<String> rearrangeInstruction = new ArrayList<>();

	public Set<String> curedDiseases = new HashSet<>();
	public Map<String, CityOLD> cities = new HashMap<>();// TODO refactor as game data
	public List<Board.Roles> roles = new ArrayList<>();// TODO refactor as game data
	public List<Player> currentPlayers = new ArrayList<>();
	public List<PlayerCard> validPlayerCards = new ArrayList<>();
	public List<String> validInfectionCards = new ArrayList<>();
	public List<String> discardInfectionCards = new ArrayList<>();
	public Set<String> discardEventCards = new HashSet<>();

	@Deprecated // Refactor as parameter
	public int pawnTobeMoved;
	@Deprecated // Refactor as parameter
	public String newLocationName;

	public int dispatcherCase = -1;

	@Deprecated // Refactor into data
	public Messages messages;

}