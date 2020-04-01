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
	public Board() {}
	
	public enum CardType {
		CITYCARD, EVENTCARD, EPIDEMIC;
	}
	
	public enum ActionName {
		DIRECTFLIGHT, PLAYEVENTCARD, CUREDISEASE, TREATDISEASE, DRIVE,
		CHARTERFLIGHT, SHUTTLEFLIGHT, BUILDSTATION, SHAREKNOWLEDGE;
	}
	
	public enum Roles{
		SCIENTIST, MEDIC, CONTINGENCYPLANNER, DISPATCHER, OPERATIONSEXPERT, 
		QUARANTINESPECIALIST, RESEARCHER;
	}
	
	public Map<String,String> messagesToShow = new HashMap<>();
	
	public Map<Board.Roles, Player> playerTable = new HashMap<>();
	
	public ListOfCityWithColorGenerator colorConcator = new ListOfCityWithColorGenerator();
	public ActionName actionName;
	public int playernumber = 0;
	public int initialhandcard = 0;
	public int epidemicCardNum = 0;
	public int outbreakMark = 0;
	public Map<String, City> currentResearchStation = new HashMap<>();
	public Stack<Integer> infectionRateTracker = new Stack<>();
	public Map<String, Integer> remainDiseaseCube = new HashMap<>();
	public Set<String> eradicatedColor = new HashSet<>();
	public EventCardAction eventCardAction;
	
	public String eventCardName;
	public String cityCardNameDirect;
	public String cityCardNameCharter;
	public String diseaseBeingTreated;
	public String driveDestinationName;
	public String shuttleDestinationName;
	public Object stationToRemove;
	public Player playerToShare;
	public PlayerCard cityToShare;
	public Map<String, EventCard> eventCards = new HashMap<>();
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
	public List<String> rearrangeInstruction = new ArrayList<>();

	public Set<String> curedDiseases = new HashSet<>();
	public Map<String, City> cities = new HashMap<>();
	public List<Board.Roles> roles = new ArrayList<>();
	public List<Player> currentPlayers = new ArrayList<>();
	public List<PlayerCard> validPlayerCards = new ArrayList<>();
	public List<String> validInfectionCards = new ArrayList<>();
	public List<String> discardInfectionCards = new ArrayList<>();
	public Set<String> discardEventCards = new HashSet<>();

	public int pawnTobeMoved;
	public String newLocationName;

	public int dispatcherCase = -1;

	public Messages messages;

}