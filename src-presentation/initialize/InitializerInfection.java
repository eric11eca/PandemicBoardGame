package initialize;

import game.GameState;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;

public class InitializerInfection {
	private GameState game;
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;

	public InitializerInfection(GameState game, Deck<CardCity> infectionDeck, Deck<CardCity> infectionDiscard) {
		super();
		this.game = game;
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
	}

	public void initialize() {
		for (int i = 3; i >= 1; i--) {
			for (int j = 0; j < 3; j++) {
				this.initialInfect(i);
			}
		}
	}

	private void initialInfect(int cubes) {
		City city = infectionDeck.takeTopCard().getCity().get();
		for (int i = 0; i < cubes; i++)
			city.infect(game, c -> false);
		infectionDiscard.putOnTop(new CardCity(city));
	}
}
