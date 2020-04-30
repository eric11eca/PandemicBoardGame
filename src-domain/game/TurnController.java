package game;

import java.util.Arrays;
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
import game.player.action.ActionType;
import lang.I18n;

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

	private int currentPlayerIndex;
	private boolean skipInfection;

	private Set<ActionType> actionDone;

	private TurnStage stage;

	private int remaining;

	public TurnController(Deck playerDeck, Infection infection, GameState game, PlayerController[] playerControllers,
			PlayerInteraction interaction) {
		super();
		this.playerDeck = playerDeck;
		this.infection = infection;
		this.interaction = interaction;
		this.game = game;
		this.playerControllers = playerControllers;
		actionDone = EnumSet.noneOf(ActionType.class);
		stage = TurnStage.BEFORE_PLAYER_ACTION;
	}

	public TurnStage getStage() {
		return stage;
	}

	/**
	 * Execute the next stage of the turn
	 */
	public void nextStage() {
		switch (stage) {
		case BEFORE_PLAYER_ACTION:
			startPlayerActionStage();
			break;
		case PLAYER_ACTION:
			stage = TurnStage.BEFORE_DRAW_CARD;
			break;
		case BEFORE_DRAW_CARD:
			startDrawingCardStage();
			break;
		case DRAWING_CARD:
			drawNextCardForPlayer();
			if (!shouldDrawMoreCardInThisTurn()) {
				stage = TurnStage.BEFORE_INFECTION;
			}
			break;
		case BEFORE_INFECTION:
			startInfectionStage();
			break;
		case INFECTION:
			nextInfection();
			if (!shouldInfectMoreInThisTurn()) {
				incrementPlayerIndex();
				stage = TurnStage.BEFORE_PLAYER_ACTION;
			}
			break;
		}
	}

	/**
	 * Starts a player's turn
	 */
	private void startPlayerActionStage() {
		final int ACTION_PER_TURN = GameProperty.getInstance().getInt("ACTION_PER_TURN");
		remaining = ACTION_PER_TURN;
		actionDone.clear();
		stage = TurnStage.PLAYER_ACTION;
	}

	private boolean canPerforMoreActionInThisTurn() {
		return remaining > 0;
	}

	private void startDrawingCardStage() {
		final int DRAW_CARDS_PER_TURN = GameProperty.getInstance().getInt("DRAW_CARDS_PER_TURN");
		remaining = DRAW_CARDS_PER_TURN;
		stage = TurnStage.DRAWING_CARD;
	}

	/**
	 * Draw a card for the player
	 * 
	 * @return true if the drawing phase ends after this call
	 */
	private void drawNextCardForPlayer() {
		if (playerDeck.isEmpty()) {
			game.triggerLose();
			return;
		}
		List<Card> cards = Arrays.asList(playerDeck.takeTopCard());
		interaction.displayCards(cards, I18n.format("turn.draw_cards"));
		playerControllers[currentPlayerIndex].givePlayerCards(cards);
		remaining--;
	}

	private boolean shouldDrawMoreCardInThisTurn() {
		return remaining > 0;
	}

	private void startInfectionStage() {
		if (skipInfection) {
			skipInfection = false;
			stage = TurnStage.BEFORE_PLAYER_ACTION;
		} else {
			remaining = game.getInfectionRate();
			stage = TurnStage.INFECTION;
		}
	}

	/**
	 * Infect a city from the infection deck
	 * 
	 * @return true if infection ends after this call
	 */
	private void nextInfection() {
		infection.infectOnce().map(city -> {
			Set<City> set = new HashSet<>();
			set.add(city);
			return set;
		}).ifPresent(cities -> {
			interaction.displayCities(cities, I18n.format("turn.infection"));
		});
		remaining--;
	}

	private boolean shouldInfectMoreInThisTurn() {
		return remaining > 0;
	}

	public void performAction(ActionType actionType) {
		assert canPerformAction(actionType);
		playerControllers[currentPlayerIndex].perform(actionType, () -> {
			actionDone(actionType);
		});
	}

	private void actionDone(ActionType actionType) {
		if (actionType != ActionType.EVENT)
			remaining--;
		actionDone.add(actionType);
	}

	public boolean canPerformAction(ActionType actionType) {
		if (actionType != ActionType.EVENT) {
			if (stage != TurnStage.PLAYER_ACTION)
				return false;
			if (!canPerforMoreActionInThisTurn())
				return false;
		}
		return playerControllers[currentPlayerIndex].canPerform(actionType, actionDone.contains(actionType));
	}

	public void skipNextInfectionStage() {
		skipInfection = true;
	}

	public boolean isNextInfectionStageSkipped() {
		return skipInfection;
	}

	private void incrementPlayerIndex() {
		currentPlayerIndex = (currentPlayerIndex + 1) % playerControllers.length;
	}

	public int getRemainingOperations() {
		return remaining;
	}

	public boolean isPlayerActive(PlayerController controller) {
		return playerControllers[currentPlayerIndex] == controller;
	}

}
