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
import game.cards.Deck;
import game.city.City;
import game.city.CitySet;
import game.disease.CityCubeData;
import game.disease.CubeData;
import game.disease.CureSet;
import game.disease.GameCubePool;
import game.player.Player;
import game.player.PlayerController;
import gui.GUIInteraction;
import gui.GameGUI;
import render.Render;
import render.RenderCity;
import render.RenderPlayer;

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

	private GUIInteraction interaction;
	private Predicate<City> quarantineChecker;
	private Infection infection;
	private Epidemic epidemic;
	private int epidemicCount;

	private PlayerInitialization playerInitialization;

	public InitializationFacade(int playerCount, int epidemicCount) throws IOException {
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
		interaction = new GUIInteraction();
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
		playerInitialization = new PlayerInitialization(startingCity, interaction, playerDiscard, citySet,
				curedDiseases, players);
		playerControllers = playerInitialization.createPlayersWithRandomRoles(playerCount);
		quarantineChecker = playerInitialization.getQuanrantineChecker();
		infection = new Infection(infectionDeck, infectionDiscard, quarantineChecker, game, gameCubePool);
		epidemic = new Epidemic(infectionDeck, infectionDiscard, game, quarantineChecker, gameCubePool);
		turnController = new TurnController(playerDeck, infection, game, playerControllers, interaction);
		initialize();
	}

	private void initialize() {
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

//	public void startGame() {
//		turnController.startPlayerActionStage();
//	}

	public GameGUI createGUI() {
		GameGUI gui = new GameGUI(game);
		RenderPlayer[] renderPlayers = createPlayerRenderers();
		Render render = new Render(renderCities, renderPlayers);
		gui.initActionPanel(turnController);
		gui.initBoardPanel(render, turnController);
		gui.initDeckPanel(playerDeck, playerDiscard, infectionDeck, infectionDiscard);
		gui.initPlayerPanel(render, playerControllers, turnController);
		gui.initStatusPanel(curedDiseases, gameCubePool, render);
		interaction.initialize(gui, render);
		return gui;
	}

	public RenderPlayer[] createPlayerRenderers() {
		RenderPlayer[] playerRenderers = new RenderPlayer[playerControllers.length];
		for (int i = 0; i < playerControllers.length; i++) {
			playerRenderers[i] = new RenderPlayer(i, playerControllers[i], turnController);
		}
		return playerRenderers;
	}

}
