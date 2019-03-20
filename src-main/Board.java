import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Board {
	int outbreak_count = 0;
	int infection_rate;
	
	int green_cubes = 24;
	int red_cubes = 24;
	int blue_cubes = 24;
	int black_cubes = 24;
	
	boolean green_cure = false;
	boolean red_cure = false;
	boolean blue_cure = false;
	boolean black_cure = false;
	
	Map<String, City> cities = new HashMap<String, City>();
	
	Set<Player> current_players;
	Set<String> valid_playerCard;
	Set<String> discard_playerCard = new HashSet<String>();
	Set<String> valid_infection_card;
	Set<String> discard_infection_card = new HashSet<String>();
	
}
