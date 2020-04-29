package game.city;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import data.CityData;
import game.GameState;
import game.GameColor;
import game.disease.CubeData;

/**
 * A city
 */
public class City {

	private CityData data;
	private Set<City> neighbors;
	private CubeData disease;
	private boolean researchStation;

	public City(CityData data, CubeData cityDiseaseCubes, Set<City> neighbors) {
		this.data = data;
		this.disease = cityDiseaseCubes;
		this.researchStation = false;
		this.neighbors = neighbors;
	}

	/**
	 * Initialize the disease cubes during setup
	 * 
	 * @param count how many cubes to put on
	 */
	public void initializeDisease(int count) {
		disease.setDiseaseCubeCount(getColor(), count);
	}

	public String getName() {
		return data.getCityName();
	}

	public int getDiseaseCubeCount(GameColor color) {
		return disease.getDiseaseCubeCount(color);
	}

	public Set<GameColor> getExistingDiseases() {
		return disease.getExistingDiseases();
	}

	public GameColor getColor() {
		return data.getColor();
	}

	public int getPopulation() {
		return data.getPopulation();
	}

	public boolean hasResearchStation() {
		return researchStation;
	}

	public void buildResearchStation() {
		this.researchStation = true;
	}

	public void removeResearchStation() {
		this.researchStation = false;
	}

	/**
	 * Performs epidemic infection on this city. Outbreaks will be automatically
	 * triggered and recorded by the {@link GameState}.
	 * 
	 * @param game          the GameState object
	 * @param isQuarantined The quarantine tester.
	 */
	public void epidemicInfect(GameState game, Predicate<City> isQuarantined) {
		int outbreakCount = epidemicInfectAndGetOutbreakCount(isQuarantined);
		game.increaseOutbreakLevel(outbreakCount);
	}

	/**
	 * Performs nromal infection on this city. Outbreaks will be automatically
	 * triggered and recorded by the {@link GameState}.
	 * 
	 * @param game          the GameState object
	 * @param isQuarantined The quarantine tester.
	 */
	public void infect(GameState game, Predicate<City> isQuarantined) {
		int outbreakCount = infectAndGetOutbreakCount(getColor(), isQuarantined);
		game.increaseOutbreakLevel(outbreakCount);
	}

	// return how many outbreaks happened
	private int epidemicInfectAndGetOutbreakCount(Predicate<City> isQuarantined) {
		if (isQuarantined.test(this))
			return 0;

		boolean willOutbreak = disease.getDiseaseCubeCount(getColor()) > 0;
		int addCubeCount = 3 - disease.getDiseaseCubeCount(getColor());
		disease.addDiseaseCube(getColor(), addCubeCount);

		if (willOutbreak) {
			return this.outbreak(getColor(), isQuarantined, new HashSet<>(), 1);
		} else {
			return 0;
		}
	}

	// return how many outbreaks happened
	private int infectAndGetOutbreakCount(GameColor diseaseColor, Predicate<City> isQuarantined) {
		return infectAndGetOutbreakCountHelper(diseaseColor, isQuarantined, new HashSet<>(), 0);
	}

	private int infectAndGetOutbreakCountHelper(GameColor diseaseColor, Predicate<City> isQuarantined,
			Set<City> inOutBreak, int outbreakCount) {
		if (isQuarantined.test(this))
			return outbreakCount;

		boolean willOutbreak = disease.getDiseaseCubeCount(diseaseColor) == 3;

		if (willOutbreak) {
			return this.outbreak(diseaseColor, isQuarantined, inOutBreak, outbreakCount + 1);
		} else {
			disease.addDiseaseCube(diseaseColor);
		}
		return outbreakCount;
	}

	private int outbreak(GameColor outbreakColor, Predicate<City> isQuarantined, Set<City> inOutBreak,
			int outbreakCount) {
		inOutBreak.add(this);
		for (City city : neighbors) {
			if (!inOutBreak.contains(city)) {
				outbreakCount = city.infectAndGetOutbreakCountHelper(outbreakColor, isQuarantined, inOutBreak,
						outbreakCount);
			}
		}
		return outbreakCount;
	}

	/**
	 * Remove 1 disease cube of the specified color
	 * 
	 * @param color
	 */
	public void treatDisease(GameColor color) {
		disease.removeDiseaseCube(color);
	}

	/**
	 * Remove all disease cubes of the specified color
	 * 
	 * @param color
	 */
	public void eradicateDisease(GameColor color) {
		disease.removeAllDiseaseCube(color);
	}

	/**
	 * @return true if there is any disease cubes on this city
	 */
	public boolean hasDisease() {
		return !disease.getExistingDiseases().isEmpty();
	}

	/**
	 * Test if two cities are neighbors
	 * 
	 * @param other another city
	 * @return true if the other city is a neighbor of this city
	 */
	public boolean isNeighboring(City other) {
		return neighbors.contains(other);
	}

	/**
	 * @return true if this city is where players should start
	 */
	public boolean isStartingCity() {
		return data.isStart();
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
