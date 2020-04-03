package game;

import java.util.Set;

public class GameSetup {
	private int playerCount;
	private int epidemic;
	private Set<City> cities;
	private City startCity;

	public GameSetup(int playerCount, int epidemic, Set<City> cities) {
		this.playerCount = playerCount;
		this.epidemic = epidemic;
		this.cities = cities;
	}

	public void setup() {
		// TODO move to initialization facade.
//		Game.reset();
//		Game game = Game.getInstance();
//
//		game.initializeCities(cities);
//		game.initializeDiseaseCubes();
//		game.initializeDecksWithoutEpidemic();
//
//		findStartingCity();
//		startCity.buildResearchStation();
//		Game.getInstance().setInitialized();
	}

	private void findStartingCity() {
		for (City c : cities) {
			if (c.isStartingCity()) {
				startCity = c;
				return;
			}
		}
		throw new RuntimeException("No Starting City");
	}

}
