package game.city;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import data.Board;
import data.CityData;
import data.GameColor;
import game.Game;

public class City {

	private CityData data;
	private Set<City> neighbors;
	private CubeData disease;
	private boolean researchStation;

	// TODO what is this for?
	public boolean underQuarantine = false;

	// ===UNDER CONSTRUCTION===//
	// This could result in data duplication/scattering
	@Deprecated
	public Set<Board.Roles> currentRoles = new HashSet<>();
	// ===UNDER CONSTRUCTION===//

	public City(CityData data, Set<City> neighbors) {
		this.data = data;
		disease = new CubeData();
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

	public void epidemicInfect(GameColor diseaseColor) {

		boolean willOutbreak = disease.getDiseaseCubeCount(diseaseColor) > 0;
		int addCubeCount = 3 - disease.getDiseaseCubeCount(diseaseColor);
		Game.getInstance().takeCubeFromPool(diseaseColor, addCubeCount);
		disease.addDiseaseCube(diseaseColor, addCubeCount);

//		/* ====UNDER CONSTRUCTION==== */
//		int remainingCubes = board.remainDiseaseCube.get(diseaseColor.compatibility_ColorString);
//		if (remainingCubes < addCubeCount) {
//			String errorMessage = MessageFormat.format("OutOf{0}", diseaseColor.compatibility_ColorString);
//			throw new RuntimeException(errorMessage);// FIXME Should not use Exceptions to run game mechanics
//		}
//		
//		board.remainDiseaseCube.put(diseaseColor.compatibility_ColorString, remainingCubes - addCubeCount);
//
//		/* ====UNDER CONSTRUCTION==== */

		if (willOutbreak) {
			this.outbreak(diseaseColor, new HashSet<>());
		}
	}

	public void infect(GameColor diseaseColor) {
		infect(diseaseColor, new HashSet<>());
	}

	private void infect(GameColor diseaseColor, Set<City> inOutBreak) {
		if (currentRoles.contains(Board.Roles.QUARANTINESPECIALIST)) {
			return;
		}

		for (City neighbor : neighbors) {
			if (neighbor.currentRoles.contains(Board.Roles.QUARANTINESPECIALIST)) {
				return;
			}
		}
		boolean willOutbreak = disease.getDiseaseCubeCount(diseaseColor) == 3;
//		int numOfCubes = 1;
//		if (disease.hasDiseaseCube(diseaseColor)) {
//			numOfCubes = city.diseaseCubes.get(diseaseColor);
//			numOfCubes += 1;
//		}

		if (willOutbreak) {
			this.outbreak(diseaseColor, inOutBreak);
		} else {
			Game.getInstance().takeCubeFromPool(diseaseColor, 1);
			disease.addDiseaseCube(diseaseColor);
		}

		// city.diseaseCubes.put(diseaseColor, numOfCubes);
	}
//
//	public List<City> infectConnectedCities(City currentCity) {
//
//		return continueOutbreak;
//	}

//	public void continueRestOfOutbreaks(List<City> continueOutbreak) {
//		for (int i = 0; i < continueOutbreak.size(); i++) {
//			City city = continueOutbreak.get(i);
//			performeOutbreak(city);
//		}
//	}

	private void outbreak(GameColor outbreakColor, Set<City> inOutBreak) {
		inOutBreak.add(this);
		Game.getInstance().moveOutbreakMarkForward();
//		List<City> continueOutbreak = new ArrayList<>();
		for (City city : neighbors) {
			if (!inOutBreak.contains(city)) {
				city.infect(outbreakColor, inOutBreak);
//				int currentNum = city.diseaseCubes.get(disease);
//				if (currentNum >= 3) {
//					continueOutbreak.add(city);
//				} else {
//					if (board.remainDiseaseCube.get(disease) == 0) {
//						String errorMessage = MessageFormat.format("OutOf{0}", disease);
//						throw new RuntimeException(errorMessage);
//					}
//					city.diseaseCubes.put(disease, currentNum + 1);
//					int remainingCube = board.remainDiseaseCube.get(disease);
//					board.remainDiseaseCube.put(disease, remainingCube - 1);
//					if (city.diseaseCubes.get(disease) == 3) {
//						continueOutbreak.add(city);
//					}
//				}
			}
		}
	}

	public void treatDisease(GameColor color) {
		Game.getInstance().putCubeToPool(color, 1);
		disease.removeDiseaseCube(color);
	}

	public void eradicateDisease(GameColor color) {
		Game.getInstance().putCubeToPool(color, disease.getDiseaseCubeCount(color));
		disease.removeAllDiseaseCube(color);
	}

	public boolean isNeighbor(City other) {
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
