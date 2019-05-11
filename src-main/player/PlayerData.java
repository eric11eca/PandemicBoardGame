package player;

import java.util.HashMap;
import java.util.Map;

import cards.PlayerCard;
import initialize.Board;
import initialize.City;
import playerAction.SpecialSkill;

public class PlayerData {
	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public Board.Roles role;
	public int action;
	public SpecialSkill specialSkill;
	public DiscoverCure discoverCure;
	public StationBuilder buildStationModel;
	public Treat treatAction;
	public String specialEventCard;
}