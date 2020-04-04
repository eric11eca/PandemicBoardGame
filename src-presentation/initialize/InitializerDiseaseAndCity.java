package initialize;

import game.city.City;
import game.disease.GameCubePool;

public class InitializerDiseaseAndCity {
	private GameCubePool gameCubePool;

	private City startingCity;

	public InitializerDiseaseAndCity(GameCubePool gameCubePool, City startingCity) {
		super();
		this.gameCubePool = gameCubePool;
		this.startingCity = startingCity;
	}

	public void initialize() {
		gameCubePool.initialize();
		startingCity.buildResearchStation();
	}
}
