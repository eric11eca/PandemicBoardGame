package initialize;

import game.GameState;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;

public class InitializerInfection {
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;

	public InitializerInfection(Deck<CardCity> infectionDeck, Deck<CardCity> infectionDiscard) {
		super();
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
		city.initializeDisease(cubes);
		infectionDiscard.putOnTop(new CardCity(city));
	}
}
