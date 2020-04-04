package initialize;

import game.Epidemic;
import game.cards.Card;
import game.cards.CardEpidemic;
import game.cards.Deck;

public class InitializerEpidemic {
	private int epidemicCount;
	private Deck<Card> playerDeck;
	private Epidemic epidemic;

	public InitializerEpidemic(int epidemicCount, Deck<Card> playerDeck, Epidemic epidemic) {
		super();
		this.epidemicCount = epidemicCount;
		this.playerDeck = playerDeck;
		this.epidemic = epidemic;
	}

	public void initialize() {
		for (int i = 0; i < epidemicCount; i++) {
			playerDeck.putOnTop(new CardEpidemic(epidemic));
		}
		playerDeck.shuffle();
	}
}
