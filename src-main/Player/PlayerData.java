package Player;

import java.util.HashMap;
import java.util.Map;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public class PlayerData {
	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public Board.Roles role;
	public int action;
	public SpecialSkill specialSkill;
	public PlayerCard specialEventCard;
	public DiscoverCure discoverCure;
	public StationBuilder buildStationModel;
	public Treat treatAction;
}