package initialize;

import java.util.Set;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.disease.GameCubePool;

public class GameInitializer {
	private int playerCount;
	private GameCubePool gameCubePool;
	private CitySet cities;
	private City startingCity;
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Deck<Card> playerDeck;

	public void setup() {
		gameCubePool.initialize();
		findStartingCity();
		startingCity.buildResearchStation();

//		game.initializeDecksWithoutEpidemic();
//
//		findStartingCity();
//		startCity.buildResearchStation();
//		Game.getInstance().setInitialized();
	}

	private void findStartingCity() {
		Set<City> startingCities = cities.getCitiesSatisfying(city -> city.isStartingCity());
		if (startingCities.size() != 1)
			throw new RuntimeException("Incorrect number of starting city: " + startingCities.size());
		startingCity = startingCities.iterator().next();
	}
}
