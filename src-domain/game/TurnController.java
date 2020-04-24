package game;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.city.City;
import game.player.PlayerController;
import game.player.PlayerInteraction;

/**
 * A upper level component that implements turns and delegate actions to the
 * player controllers
 */
public class TurnController {
	private Deck playerDeck;
	private Infection infection;
	private GameState game;
	private PlayerController[] playerControllers;
	private PlayerInteraction interaction;

	private int current;
	private boolean skipInfection;
	private int remainingActions;
	private boolean isInfectionStage;
	private int remainingInfection;

	private Set<ActionType> actionDone;

	public TurnController(Deck playerDeck, Infection infection, GameState game, PlayerController[] playerControllers,
			PlayerInteraction interaction) {
		super();
		this.playerDeck = playerDeck;
		this.infection = infection;
		this.interaction = interaction;
		this.game = game;
		this.playerControllers = playerControllers;
		actionDone = EnumSet.noneOf(ActionType.class);
	}

	public void startTurn() {
		final int ACTION_PER_TURN = GameProperty.getInstance().getInt("ACTION_PER_TURN");
		remainingActions = ACTION_PER_TURN;
		actionDone.clear();
		isInfectionStage = false;
	}

	public boolean canContinueAction() {
		return remainingActions > 0 && !isInfectionStage;
	}

	public void performAction(ActionType actionType) {
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
		if (!canContinueAction() && actionType != ActionType.EVENT)
			return false;
		return playerControllers[current].canPerform(actionType, actionDone.contains(actionType));
	}

	public void skipNextInfectionStage() {
		skipInfection = true;
	}

	public void endTurn() {
		if (!isInfectionStage) {
			drawPlayerCards();
			startInfection();
		} else {
			if (remainingInfection > 0) {
				infection.infectOnce().map(city -> {
					Set<City> set = new HashSet<>();
					set.add(city);
					return set;
				}).ifPresent(cities -> {
					interaction.displayCities(cities, "turn.infection");
				});
				remainingInfection--;
			}
			if (remainingInfection == 0) {
				nextTurn();
			}
		}

	}

	public boolean isInfectionStage() {
		return isInfectionStage;
	}

	public void startInfection() {
		if (!skipInfection) {
			isInfectionStage = true;
			remainingInfection = game.getInfectionRate();
		} else {
			isInfectionStage = true;
			skipInfection = false;
		}
	}

	public void nextTurn() {
		isInfectionStage = false;
		current = (current + 1) % playerControllers.length;
		startTurn();
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
		interaction.displayCards(cards, "turn.draw_cards");
		playerControllers[current].givePlayerCards(cards);
	}

	public int getRemainingActions() {
		return remainingActions;
	}

	public boolean isPlayerActive(PlayerController controller) {
		return playerControllers[current] == controller;
	}

	public int getRemainingInfection() {
		return remainingInfection;
	}

}
