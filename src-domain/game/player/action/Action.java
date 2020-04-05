package game.player.action;

import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

/**
 * Abstract of an action, which is what a player does. Actions are package by
 * {@link PlayerController}
 */
public abstract class Action {
	private Player player;
	protected PlayerInteraction interaction;

	public Action(Player player, PlayerInteraction interaction) {
		super();
		this.player = player;
		this.interaction = interaction;
	}

	protected Player player() {
		return player;
	}

	protected City playerCurrentLocation() {
		return player().getLocation();
	}

	/**
	 * Perform this action, which may cause asynchronous calls. When the action is
	 * complete, the callback will be invoked. Note that the callback may never be
	 * called as a cancellation of the action or when exception occurs
	 * 
	 * @param completionCallback the callback function invoked after completion
	 */
	public abstract void perform(Runnable completionCallback);

	public abstract boolean canPerform();

	public boolean isOncePerTurn() {
		return false;
	}
}
