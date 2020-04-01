package game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import data.Board;
import data.CityData;
import data.GameColor;
import data.Board.Roles;

public class City {

	@Deprecated // Graphical Data
	public int x;
	@Deprecated // Graphical Data
	public int y;

	// TODO delegate access
	public CityData data;

	public Set<City> neighbors;

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

	private City() {
		this.diseaseCubes = new HashMap<String, Integer>();
		this.researchStation = false;
		this.neighbors = new HashSet<>();
	}

	public City(CityData data, int x, int y) {
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
		return obj != null && obj.getClass() == getClass() && Objects.equals(((City) obj).data, data);
	}
}
