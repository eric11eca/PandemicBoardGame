package data;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class City {
	public int x;
	public int y;
	public String cityName;
	public String color;
	public int population;
	public Map<String, Integer> diseaseCubes;
	public boolean researchStation;
	public Map<String, City> neighbors;
	public boolean isInOutbreak = false;
	public boolean underQuarantine = false;
	public Set<Board.Roles> currentRoles = new HashSet<>();

	public City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashMap<>();
	}
	
	public City(String name){
		this();
		this.cityName = name;
	}
	
	public City(String name, String color){
		this(name);
		this.color = color;
	}
	
	public City(String name, String color, int population, int x, int y){
		this(name,color);
		this.x = x;
		this.y = y;
		this.population = population;
	}
}
