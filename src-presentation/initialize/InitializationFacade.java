package initialize;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;

import game.GameColor;
import game.GameState;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.CityCubeData;
import game.disease.CubeData;
import game.disease.CureSet;
import game.disease.GameCubePool;
import gui.ActionUI;
import gui.BoardUI;
import gui.DeckUI;
import gui.DiseaseUI;
import gui.EventUI;
import gui.GameGUI;
import gui.LogoUI;
import gui.OutbreakUI;
import parse.CityLoader;
import render.RenderCity;

public class InitializationFacade {
	private GameState game;
	private GameCubePool gameCubePool;
	private HashMap<String, City> cities;
	private HashMap<City, RenderCity> renderCities;
	private CityLoader cityLoader;
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Deck<Card> playerDeck;
	private Deck<Card> playerDiscard;
	private Set<GameColor> curedDiseases;
//	private LogoUI logoUI;
//	private OutbreakUI outbreakUI;
//	private DiseaseUI diseaseUI;
//	private EventUI eventUI;
//	private DeckUI deckUI;
//	private ActionUI actionUI;
//	private BoardUI boardUI;
//	private GameGUI gui;

	public InitializationFacade() {
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
		infectionDeck = new Deck<>();
		infectionDiscard = new Deck<>();
		playerDeck = new Deck<>();
		playerDiscard = new Deck<>();
		curedDiseases = new CureSet(game, EnumSet.noneOf(GameColor.class));

	}
}
