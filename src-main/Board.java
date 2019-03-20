import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	List<PlayerCard> valid_playerCard = new ArrayList<PlayerCard>();
	List<PlayerCard> discard_playerCard = new ArrayList<PlayerCard>();
	List<String> valid_infection_card = new ArrayList<String>();
	List<String> discard_infection_card = new ArrayList<String>();
}
