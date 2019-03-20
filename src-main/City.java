import java.util.HashMap;
import java.util.HashSet;

public class City {
	int X;
	int Y;
	
	String name;
	String color;
	HashMap<String, Integer> diseaseCubes;
	Boolean researchStation;
	HashSet<City> neighbors;

	public City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<City>();
	}
}
