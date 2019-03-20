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
	
	ArrayList<City> cities;
	
	ArrayList<Player> current_players;
	ArrayList<String> valid_playerCard;
	ArrayList<String> discard_playerCard = new ArrayList<String>();
	ArrayList<String> valid_infection_card;
	ArrayList<String> discard_infection_card = new ArrayList<String>();
}
