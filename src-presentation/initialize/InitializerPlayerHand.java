package initialize;

import java.util.ArrayList;
import java.util.List;

import game.cards.Card;
import game.cards.Deck;
import game.player.PlayerController;

public class InitializerPlayerHand {
	private PlayerController[] playerControllers;

	private Deck playerDeck;

	public InitializerPlayerHand(PlayerController[] playerControllers, Deck playerDeck) {
		super();
		this.playerControllers = playerControllers;
		this.playerDeck = playerDeck;
	}

	public void initialize() {
		for (PlayerController controller : playerControllers) {
			int handSize = 6 - playerControllers.length;
			List<Card> initialHand = new ArrayList<>();
			for (int i = 0; i < handSize; i++) {
				initialHand.add(playerDeck.takeTopCard());
			}
			controller.givePlayerCards(initialHand);
		}
	}
}
