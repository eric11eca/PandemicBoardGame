import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Board {
	public enum CardType{
		CITYCARD,
		EVENTCARD,
		EPIDEMIC;
	}
	
	int outbreak_count = 0;
	int infection_rate;
	
	int yellow_cubes = 24;
	int red_cubes = 24;
	int blue_cubes = 24;
	int black_cubes = 24;
	
	boolean yellow_cure = false;
	boolean red_cure = false;
	boolean blue_cure = false;
	boolean black_cure = false;
	
	Map<String, City> cities = new HashMap<String, City>();
	List<Player> current_players = new ArrayList<Player>();
	List<Player> roles = new ArrayList<Player>();
	Set<PlayerCard> valid_playerCard = new HashSet<PlayerCard>();
	Set<PlayerCard> discard_playerCard = new HashSet<PlayerCard>();
	Set<String> valid_infection_card = new HashSet<String>();
	Set<String> discard_infection_card = new HashSet<String>();
}
