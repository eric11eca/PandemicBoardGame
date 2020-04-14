package initialize;

import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import data.CityLoader;
import game.Epidemic;
import game.GameColor;
import game.GameState;
import game.Infection;
import game.TurnController;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.disease.CityCubeData;
import game.disease.CubeData;
import game.disease.CureSet;
import game.disease.GameCubePool;
import game.player.Player;
import game.player.PlayerController;
import game.player.PlayerInteraction;
import render.RenderCity;

public class InitializationFacade {
	private GameState game;
	private GameCubePool gameCubePool;
	private HashMap<String, City> cities;
	private HashMap<City, RenderCity> renderCities;
	private CityLoader cityLoader;
	private Deck infectionDeck;
	private Deck infectionDiscard;
	private Deck playerDeck;
	private Deck playerDiscard;
	private Set<GameColor> curedDiseases;
	private List<Player> players;
	private CitySet citySet;
	private City startingCity;
	private PlayerController[] playerControllers;
	private TurnController turnController;
	private PlayerFactory playerFactory;
	private PlayerInteraction interaction;
	private Predicate<City> quarantineChecker;
	private Infection infection;
	private Epidemic epidemic;
	private int playerCount;
	private int epidemicCount;
//	private LogoUI logoUI;
//	private OutbreakUI outbreakUI;
//	private DiseaseUI diseaseUI;
//	private EventUI eventUI;
//	private DeckUI deckUI;
//	private ActionUI actionUI;
//	private BoardUI boardUI;
//	private GameGUI gui;

	public InitializationFacade(int playerCount, int epidemicCount) throws IOException {
		this.playerCount = playerCount;
		this.epidemicCount = epidemicCount;
		game = new GameState();
		gameCubePool = new GameCubePool(game);
		cities = new HashMap<>();
		renderCities = new HashMap<>();
		cityLoader = new CityLoader(cities, renderCities) {
			@Override
			protected CubeData createCubeData() {
				return new CityCubeData(gameCubePool);
			}
		};
		cityLoader.loadCities();
		infectionDeck = new Deck();
		infectionDiscard = new Deck();
		playerDeck = new Deck();
		playerDiscard = new Deck();
		curedDiseases = new CureSet(game, EnumSet.noneOf(GameColor.class));
		players = new LinkedList<>();
		citySet = new CitySet(new HashSet<>(cities.values()));
		Set<City> set = citySet.getCitiesSatisfying(c -> c.isStartingCity());
		assert set.size() == 1;
		startingCity = set.iterator().next();
		playerFactory = new PlayerFactory(startingCity, interaction, playerDiscard, citySet, curedDiseases, players);
		playerControllers = playerFactory.createPlayersWithRandomRoles(playerCount);
		quarantineChecker = playerFactory.getQuanrantineChecker();
		infection = new Infection(infectionDeck, infectionDiscard, quarantineChecker, game, gameCubePool);
		epidemic = new Epidemic(infectionDeck, infectionDiscard, game, quarantineChecker, gameCubePool);
		turnController = new TurnController(playerDeck, infection, game, playerControllers);
	}

	public void initialize() {
		new InitializerDiseaseAndCity(gameCubePool, startingCity).initialize();
		new InitializerDecksWithoutEpidemic(citySet, infectionDeck, infectionDiscard, playerDeck, players,
				turnController).initialize();
		new InitializerPlayerHand(playerControllers, playerDeck).initialize();
		new InitializerInfection(infectionDeck, infectionDiscard).initialize();
		new InitializerEpidemic(epidemicCount, playerDeck, epidemic).initialize();
		determinePlayOrder();
	}

	private void determinePlayOrder() {
		Arrays.sort(playerControllers, (p1, p2) -> {
			return p2.getHighestPopulationInHand() - p1.getHighestPopulationInHand();
		});
	}

}
