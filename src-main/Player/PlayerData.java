package Player;

import java.util.HashMap;
import java.util.Map;

import Initialize.Board;
import Initialize.City;
import PlayerAction.SpecialSkill;
import cards.PlayerCard;

public class PlayerData {
	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public Board.Roles role;
	public int action;
	public SpecialSkill specialSkill;
	public String roleCard;
	public DiscoverCure discoverCure;
	public StationBuilder buildStationModel;
	public Treat treatAction;
}