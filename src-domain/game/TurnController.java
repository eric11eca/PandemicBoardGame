package game;

import java.util.ArrayList;
import java.util.List;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.player.PlayerController;

/**
 * A upper level component that implements turns and delegate actions to the
 * player controllers
 */
public class TurnController {
	private Deck<Card> playerDeck;
	private Infection infection;
	private GameState game;
	private PlayerController[] playerControllers;

	private int current;
	private boolean skipInfection;
	private int remainingActions;

	public TurnController(Deck<Card> playerDeck, Infection infection, GameState game,
			PlayerController[] playerControllers) {
		super();
		this.playerDeck = playerDeck;
		this.infection = infection;
		this.game = game;
		this.playerControllers = playerControllers;
	}

	public void startTurn() {
		final int ACTION_PER_TURN = GameProperty.getInstance().getInt("ACTION_PER_TURN");
		remainingActions = ACTION_PER_TURN;
	}

	public boolean canContinueAction() {
		return remainingActions > 0;
	}

	public void performAction(ActionType actionType) {
		if (!canContinueAction())
			throw new RuntimeException("The player has used up all actions");
		if (!playerControllers[current].canPerform(actionType))
			throw new RuntimeException("Cannot perform this action");
		playerControllers[current].perform(actionType, () -> {
			remainingActions--;
		});
	}

	public boolean canPerformAction(ActionType actionType) {
		return playerControllers[current].canPerform(actionType);
	}

	public void skipNextInfectionStage() {
		skipInfection = true;
	}

	public void endTurn() {
		drawPlayerCards();
		infection();
		current = (current + 1) % playerControllers.length;
	}

	private void drawPlayerCards() {
		List<Card> cards = new ArrayList<>();
		final int DRAW_CARDS_PER_TURN = GameProperty.getInstance().getInt("DRAW_CARDS_PER_TURN");
		for (int i = 0; i < DRAW_CARDS_PER_TURN; i++) {
			if (playerDeck.isEmpty()) {
				game.triggerLose();
				break;
			}
			cards.add(playerDeck.takeTopCard());
		}
		playerControllers[current].givePlayerCards(cards);
	}

	private void infection() {
		if (skipInfection) {
			skipInfection = false;
			return;
		}
		for (int i = 0; i < game.getInfectionRate(); i++) {
			infection.infectOnce();
		}
	}
}
