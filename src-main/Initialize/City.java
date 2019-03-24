package Initialize;
import java.util.HashMap;
import java.util.HashSet;

import Player.Player;

public class City {
	public int x;
	public int y;
	
	public String cityName;
	public String color;
	public int population;
	public HashMap<String, Integer> diseaseCubes;
	public Boolean researchStation;
	public HashSet<City> neighbors;

	public City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<City>();
	}
	
	public City(String name, String color, int population, int x, int y){
		this();
		this.x = x;
		this.y = y;
		this.cityName = name;
		this.color = color;
		this.population = population;
	}
}
