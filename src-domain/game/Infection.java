package game;

import java.util.Optional;
import java.util.function.Predicate;

import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;

/**
 * Implementation of the infection mechanics, which happens at the end of each
 * turn
 */
public class Infection {

	private Deck infectionDeck;
	private Deck infectionDiscard;
	private Predicate<City> quarantineChecker;
	private GameState game;
	private GameCubePool gameCubePool;

	public Infection(Deck infectionDeck, Deck infectionDiscard, Predicate<City> quarantineChecker, GameState game,
			GameCubePool gameCubePool) {
		super();
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
		this.quarantineChecker = quarantineChecker;
		this.game = game;
		this.gameCubePool = gameCubePool;
	}

	/**
	 * Execute one infection by taking the top card from infection deck and infect
	 * the city, following the game mechanics.
	 * 
	 * @return The infected city. If the infection deck is empty ,returns empty
	 *         optional
	 */
	public Optional<City> infectOnce() {
		if (infectionDeck.isEmpty()) {
			game.triggerLose();
			return Optional.empty();
		}
		Card card = infectionDeck.takeTopCard();

		City city = card.getCity().get();

		if (!gameCubePool.isDiseaseEradicated(city.getColor())) {
			city.infect(game, quarantineChecker);

		}

		card.discard(infectionDiscard);
		return Optional.of(city);
	}

}
