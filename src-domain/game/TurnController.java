package game;

import java.util.ArrayList;
import java.util.List;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.player.PlayerController;

public class TurnController {
	private Deck<Card> playerDeck;
	private Infection infection;
	private GameState game;

	private PlayerController playerController;
	private boolean skipInfection;
	private int remainingActions;

	public TurnController(Deck<Card> playerDeck, Infection infection, GameState game) {
		super();
		this.playerDeck = playerDeck;
		this.infection = infection;
		this.game = game;
	}

	public void startTurn(PlayerController playerController) {
		this.playerController = playerController;
		final int ACTION_PER_TURN = GameProperty.getInstance().getInt("ACTION_PER_TURN");
		remainingActions = ACTION_PER_TURN;
	}

	public boolean canContinueAction() {
		return remainingActions > 0;
	}

	public void performAction(ActionType actionType) {
		if (!playerController.canPerform(actionType))
			throw new RuntimeException("Cannot perform this action");
		playerController.perform(actionType);
	}

	public void skipNextInfectionStage() {
		skipInfection = true;
	}

	public void endTurn() {
		drawPlayerCards();
		infection();
		playerController = null;
	}

	private void drawPlayerCards() {
		List<Card> cards = new ArrayList<>();
		final int DRAW_CARDS_PER_TURN = GameProperty.getInstance().getInt("DRAW_CARDS_PER_TURN");
		for (int i = 0; i < DRAW_CARDS_PER_TURN; i++)
			cards.add(playerDeck.takeTopCard());
		playerController.givePlayerCards(cards);
	}

	private void infection() {
		if (skipInfection)
			return;
		for (int i = 0; i < game.getInfectionRate(); i++) {
			infection.infectOnce();
		}
	}
}
