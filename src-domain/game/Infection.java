package game;

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

	public void infectOnce() {
		if (infectionDeck.isEmpty()) {
			game.triggerLose();
			return;
		}
		Card card = infectionDeck.takeTopCard();

		City city = card.getCity().orElseThrow(RuntimeException::new);
		if (!gameCubePool.isDiseaseEradicated(city.getColor())) {
			city.infect(game, quarantineChecker);
		}

		card.discard(infectionDiscard);

	}

}
