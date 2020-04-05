package game;

import java.util.function.Predicate;

import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;

/**
 * Implementation of the infection mechanics, which happens at the end of each
 * turn
 */
public class Infection {

	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Predicate<City> quarantineChecker;
	private GameState game;
	private GameCubePool gameCubePool;

	public Infection(Deck<CardCity> infectionDeck, Deck<CardCity> infectionDiscard, Predicate<City> quarantineChecker,
			GameState game, GameCubePool gameCubePool) {
		super();
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
		this.quarantineChecker = quarantineChecker;
		this.game = game;
		this.gameCubePool = gameCubePool;
	}

	public void infectOnce() {
		if (infectionDeck.isEmpty()) {
			game.triggerLose();
			return;
		}
		CardCity card = infectionDeck.takeTopCard();

		City city = card.getCity().orElseThrow(RuntimeException::new);
		if (!gameCubePool.isDiseaseEradicated(city.getColor())) {
			city.infect(game, quarantineChecker);
		}

		card.discard(infectionDiscard, CardCity.class);

	}

}
