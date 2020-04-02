package game;

import java.util.EnumSet;
import java.util.Set;

import data.GameColor;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.city.CubeData;

public class Game {
	private static Game instance;

	public static final int MAX_CUBE_IN_POOL = 24;

	public static void reset() {
		instance = null;
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	private boolean initialized;
	private CubeData diseaseCubes;
	private boolean lost;
	private boolean won;
	private int outbreakMark;
	private Deck<CardCity> infectionDeck;

	private Deck<CardCity> infectionDiscard;
	private Deck<Card> playerDeck;
	private Deck<Card> playerDiscard;
	private Set<City> allCities;
	private Set<GameColor> curedDisease;

	private Game() {
		diseaseCubes = new CubeData();
		lost = false;
		won = false;
		outbreakMark = 0;
		infectionDeck = new Deck<>();
		infectionDiscard = new Deck<>();
		playerDeck = new Deck<>();
		playerDiscard = new Deck<>();
		initialized = false;
		curedDisease = EnumSet.noneOf(GameColor.class);
	}

	public void triggerWin() {
		won = true;
	}

	public void triggerLose() {
		lost = true;
	}

	public boolean isLost() {
		return lost;
	}

	public boolean isWon() {
		return won;
	}

	public void putCubeToPool(GameColor color, int count) {
		diseaseCubes.addDiseaseCube(color, count);
	}

	public void takeCubeFromPool(GameColor color, int count) {
		if (diseaseCubes.getDiseaseCubeCount(color) < count) {
			triggerLose();
			return;
		}
		diseaseCubes.removeDiseaseCube(color, count);
	}

	public void moveOutbreakMarkForward() {
		outbreakMark += 1;
		if (outbreakMark == 8) {
			triggerLose();
		}
	}

	public int getOutbreakMark() {
		return outbreakMark;
	}

	public boolean isDiseaseEradicated(GameColor color) {
		return diseaseCubes.getDiseaseCubeCount(color) == MAX_CUBE_IN_POOL;
	}

	public void initializeDiseaseCubes() {
		for (GameColor color : GameColor.values())
			diseaseCubes.setDiseaseCubeCount(color, MAX_CUBE_IN_POOL);
	}

	public void initializeCities(Set<City> cities) {
		this.allCities = cities;
	}

	public void initializeDecksWithoutEpidemic() {
		allCities.forEach(this::addCityCardToPlayerAndInfectionDecks);
		addEventCardsToDeck();
		shuffleDecks();
	}

	private void addCityCardToPlayerAndInfectionDecks(City city) {
		this.playerDeck.putOnTop(new CardCity(city));
		this.infectionDeck.putOnTop(new CardCity(city));
	}

	private void addEventCardsToDeck() {
		// TODO
	}

	private void addEpidemicCardsToDeck() {
		// TODO
	}

	private void shuffleDecks() {
		this.playerDeck.shuffle();
		this.infectionDeck.shuffle();
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized() {
		initialized = true;
	}

	public boolean isDiseaseCured(GameColor color) {
		return curedDisease.contains(color);
	}

	public int getInfectionDeckSize() {
		return infectionDeck.size();
	}

	public int getInfectionDiscardSize() {
		return infectionDiscard.size();
	}

	public int getPlayerDeckSize() {
		return playerDeck.size();
	}

	public int getPlayerDiscardSize() {
		return playerDiscard.size();
	}

}
