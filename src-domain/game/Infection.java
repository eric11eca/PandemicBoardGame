package game;

import java.util.function.Supplier;

import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;

public class Infection {

	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Supplier<City> quarantineSpecialistLocation;
	private GameState game;
	private GameCubePool gameCubePool;

	public Infection(Deck<CardCity> infectionDeck, Deck<CardCity> infectionDiscard,
			Supplier<City> quarantineSpecialistLocation, GameState game, GameCubePool gameCubePool) {
		super();
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
		this.quarantineSpecialistLocation = quarantineSpecialistLocation;
		this.game = game;
		this.gameCubePool = gameCubePool;
	}

	public void infectOnce() {
		CardCity card = infectionDeck.takeTopCard();

		City city = card.getCity().orElseThrow(RuntimeException::new);
		if (!gameCubePool.isDiseaseEradicated(city.getColor())) {
			city.infect(game, quarantineSpecialistLocation.get());
		}

		card.discard(infectionDiscard, CardCity.class);

	}

}
