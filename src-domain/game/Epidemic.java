package game;

import java.util.function.Predicate;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;

/**
 * Implementation of the Epidemic mechanics
 */
public class Epidemic {
	private Deck infectionCards;
	private Deck infectionDiscard;
	private GameState game;
	private Predicate<City> quarantineChecker;
	private GameCubePool gameCubePool;

	public Epidemic(Deck infectionCards, Deck infectionDiscard, GameState game, Predicate<City> quarantineChecker,
			GameCubePool gameCubePool) {
		super();
		this.infectionCards = infectionCards;
		this.infectionDiscard = infectionDiscard;
		this.game = game;
		this.quarantineChecker = quarantineChecker;
		this.gameCubePool = gameCubePool;
	}

	public void triggerEpidemic() {
		game.increaseInfectionRate();
		epidemicInfect();
		epidemicIntensify();
	}

	private void epidemicInfect() {
		City city = determineCity();

		if (!gameCubePool.isDiseaseEradicated(city.getColor())) {
			city.epidemicInfect(game, quarantineChecker);
		}

		infectionDiscard.putOnTop(new CardCity(city));
	}

	private City determineCity() {
		Card card = infectionCards.takeBottomCard();
		return card.getCity().get();
	}

	private void epidemicIntensify() {
		infectionDiscard.shuffle();
		infectionDiscard.forEach(infectionCards::putOnTop);
		infectionDiscard.clear();
	}
}
