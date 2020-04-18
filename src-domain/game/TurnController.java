package game;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.player.PlayerController;

/**
 * A upper level component that implements turns and delegate actions to the
 * player controllers
 */
public class TurnController {
	private Deck playerDeck;
	private Infection infection;
	private GameState game;
	private PlayerController[] playerControllers;

	private int current;
	private boolean skipInfection;
	private int remainingActions;

	private Set<ActionType> actionDone;

	public TurnController(Deck playerDeck, Infection infection, GameState game, PlayerController[] playerControllers) {
		super();
		this.playerDeck = playerDeck;
		this.infection = infection;
		this.game = game;
		this.playerControllers = playerControllers;
		actionDone = EnumSet.noneOf(ActionType.class);
	}

	public void startTurn() {
		final int ACTION_PER_TURN = GameProperty.getInstance().getInt("ACTION_PER_TURN");
		remainingActions = ACTION_PER_TURN;
		actionDone.clear();
	}

	public boolean canContinueAction() {
		return remainingActions > 0;
	}

	public void performAction(ActionType actionType) {
		assert canContinueAction();
		assert canPerformAction(actionType);
		playerControllers[current].perform(actionType, () -> {
			actionDone(actionType);
		});
	}

	private void actionDone(ActionType actionType) {
		if (actionType != ActionType.EVENT)
			remainingActions--;
		actionDone.add(actionType);

	}

	public boolean canPerformAction(ActionType actionType) {
		return playerControllers[current].canPerform(actionType, actionDone.contains(actionType));
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

	public int getRemainingActions() {
		return remainingActions;
	}

	public boolean isPlayerActive(PlayerController controller) {
		return playerControllers[current] == controller;
	}

}
