import java.util.HashMap;
import java.util.HashSet;

public class City {
	int X;
	int Y;
	
	String cityName;
	String color;
	HashMap<String, Integer> diseaseCubes;
	Boolean researchStation;
	HashSet<City> neighbors;

	public City(String name, String color){
		this();
		this.color = color;
		this.cityName = name;
	}
	
	public City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<City>();
	}
}
