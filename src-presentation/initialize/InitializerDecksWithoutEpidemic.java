package initialize;

import java.util.List;

import game.TurnController;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.cards.event.CardEvent;
import game.city.City;
import game.city.CitySet;
import game.event.EventAirlift;
import game.event.EventForecast;
import game.event.EventGovernmentGrant;
import game.event.EventOneQuietNight;
import game.event.EventResilientPopulation;
import game.player.Player;

public class InitializerDecksWithoutEpidemic {
	private CitySet cities;
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Deck<Card> playerDeck;
	private List<Player> players;
	private TurnController turnController;

	public InitializerDecksWithoutEpidemic(CitySet cities, Deck<CardCity> infectionDeck,
			Deck<CardCity> infectionDiscard, Deck<Card> playerDeck, List<Player> players,
			TurnController turnController) {
		super();
		this.cities = cities;
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
		this.playerDeck = playerDeck;
		this.players = players;
		this.turnController = turnController;
	}

	public void initialize() {
		cities.getAllCities().forEach(this::addCityToInfectionAndPlayerDeck);
		initializeEventCards();
		playerDeck.shuffle();
		infectionDeck.shuffle();
	}

	private void initializeEventCards() {
		playerDeck.putOnTop(new CardEvent(new EventAirlift(players, cities)));
		playerDeck.putOnTop(new CardEvent(new EventForecast(infectionDeck)));
		playerDeck.putOnTop(new CardEvent(new EventGovernmentGrant(cities)));
		playerDeck.putOnTop(new CardEvent(new EventOneQuietNight(turnController)));
		playerDeck.putOnTop(new CardEvent(new EventResilientPopulation(infectionDiscard)));
	}

	private void addCityToInfectionAndPlayerDeck(City city) {
		infectionDeck.putOnTop(new CardCity(city));
		playerDeck.putOnTop(new CardCity(city));
	}
}
