package game.city;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

	public void epidemicInfect(GameState game, City quarantineSpecialistLocation) {
		int outbreakCount = epidemicInfectAndGetOutbreakCount(quarantineSpecialistLocation);
		game.increaseOutbreakLevel(outbreakCount);
	}

	public void infect(GameState game, City quarantineSpecialistLocation) {
		int outbreakCount = infectAndGetOutbreakCount(getColor(), quarantineSpecialistLocation);
		game.increaseOutbreakLevel(outbreakCount);
	}

	private int epidemicInfectAndGetOutbreakCount(City quarantineSpecialistLocation) {
		if (isQuarantined(quarantineSpecialistLocation))
			return 0;

		boolean willOutbreak = disease.getDiseaseCubeCount(getColor()) > 0;
		int addCubeCount = 3 - disease.getDiseaseCubeCount(getColor());
		disease.addDiseaseCube(getColor(), addCubeCount);

		if (willOutbreak) {
			return this.outbreak(getColor(), quarantineSpecialistLocation, new HashSet<>(), 0);
		} else {
			return 0;
		}
	}

	private boolean isQuarantined(City quarantineSpecialistLocation) {
		return quarantineSpecialistLocation != null
				&& (quarantineSpecialistLocation.equals(this) || quarantineSpecialistLocation.isNeighboring(this));
	}

//return how many outbreaks happened
	private int infectAndGetOutbreakCount(GameColor diseaseColor, City quarantineSpecialistLocation) {
		return infectAndGetOutbreakCountHelper(diseaseColor, quarantineSpecialistLocation, new HashSet<>(), 0);
	}

	private int infectAndGetOutbreakCountHelper(GameColor diseaseColor, City quarantineSpecialistLocation,
			Set<City> inOutBreak, int outbreakCount) {
		if (isQuarantined(quarantineSpecialistLocation))
			return outbreakCount;

		boolean willOutbreak = disease.getDiseaseCubeCount(diseaseColor) == 3;

		if (willOutbreak) {
			return this.outbreak(diseaseColor, quarantineSpecialistLocation, inOutBreak, outbreakCount + 1);
		} else {
			disease.addDiseaseCube(diseaseColor);
		}
		return outbreakCount;
	}

	private int outbreak(GameColor outbreakColor, City quarantineSpecialistLocation, Set<City> inOutBreak,
			int outbreakCount) {
		inOutBreak.add(this);
		for (City city : neighbors) {
			if (!inOutBreak.contains(city)) {
				outbreakCount = city.infectAndGetOutbreakCountHelper(outbreakColor, quarantineSpecialistLocation,
						inOutBreak, outbreakCount);
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
