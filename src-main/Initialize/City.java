package Initialize;
import java.util.HashMap;
import java.util.HashSet;

public class City {
	public int x;
	public int y;
	public String cityName;
	public String color;
	public int population;
	public HashMap<String, Integer> diseaseCubes;
	public boolean researchStation;
	public HashSet<City> neighbors;

	public City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<City>();
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
