package game.city;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import data.CityData;
import game.GameState;
import game.GameColor;
import game.disease.CubeData;

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

	public void epidemicInfect(GameState game, Predicate<City> isQuarantined) {
		int outbreakCount = epidemicInfectAndGetOutbreakCount(isQuarantined);
		game.increaseOutbreakLevel(outbreakCount);
	}

	public void infect(GameState game, Predicate<City> isQuarantined) {
		int outbreakCount = infectAndGetOutbreakCount(getColor(), isQuarantined);
		game.increaseOutbreakLevel(outbreakCount);
	}

	private int epidemicInfectAndGetOutbreakCount(Predicate<City> isQuarantined) {
		if (isQuarantined(isQuarantined))
			return 0;

		boolean willOutbreak = disease.getDiseaseCubeCount(getColor()) > 0;
		int addCubeCount = 3 - disease.getDiseaseCubeCount(getColor());
		disease.addDiseaseCube(getColor(), addCubeCount);

		if (willOutbreak) {
			return this.outbreak(getColor(), isQuarantined, new HashSet<>(), 0);
		} else {
			return 0;
		}
	}

	private boolean isQuarantined(Predicate<City> isQuarantined) {
		return isQuarantined.test(this);
	}

//return how many outbreaks happened
	private int infectAndGetOutbreakCount(GameColor diseaseColor, Predicate<City> isQuarantined) {
		return infectAndGetOutbreakCountHelper(diseaseColor, isQuarantined, new HashSet<>(), 0);
	}

	private int infectAndGetOutbreakCountHelper(GameColor diseaseColor, Predicate<City> isQuarantined,
			Set<City> inOutBreak, int outbreakCount) {
		if (isQuarantined(isQuarantined))
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

	public void treatDisease(GameColor color) {
		disease.removeDiseaseCube(color);
	}

	public void eradicateDisease(GameColor color) {
		disease.removeAllDiseaseCube(color);
	}

	public boolean hasDisease() {
		return !disease.getExistingDiseases().isEmpty();
	}

	public boolean isNeighboring(City other) {
		return neighbors.contains(other);
	}

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
