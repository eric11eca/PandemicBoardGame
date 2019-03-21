package Initialize;
import java.util.HashMap;
import java.util.HashSet;

public class City {
	public int X;
	public int Y;
	
	public String cityName;
	public String color;
	public HashMap<String, Integer> diseaseCubes;
	public Boolean researchStation;
	public HashSet<City> neighbors;

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
