package data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CityOLD {

	@Deprecated // Graphical Data
	public int x;
	@Deprecated // Graphical Data
	public int y;

	// TODO delegate access
	public CityData data;

	public Set<CityOLD> neighbors;

	// TODO game data
	public Map<String, Integer> diseaseCubes;
	public boolean researchStation;

	public boolean isInOutbreak = false;
	public boolean underQuarantine = false;
	// ===UNDER CONSTRUCTION===//
	// This could result in data duplication/scattering
	@Deprecated
	public Set<Board.Roles> currentRoles = new HashSet<>();

	// ===UNDER CONSTRUCTION===//
	private CityOLD() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<>();
	}

//	public CityOLD(String name) {
//		this();
//		this.cityName = name;
//	}
//
//	public CityOLD(String name, String color) {
//		this(name);
//		this.color = color;
//	}

	public CityOLD(CityData data, int x, int y) {
		this();
		this.data = data;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return data.getCityName();
	}

	public GameColor getColor() {
		return data.getColor();
	}

	public int getPopulation() {
		return data.getPopulation();
	}

	@Override
	public int hashCode() {
		return Objects.hash(data);
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && Objects.equals(((CityOLD) obj).data, data);
	}
}
