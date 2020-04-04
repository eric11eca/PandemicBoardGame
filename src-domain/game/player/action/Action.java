package game.player.action;

import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public abstract class Action {
	private Player player;
	protected PlayerInteraction interaction;

	public Action(Player player, PlayerInteraction interaction) {
		super();
		this.player = player;
		this.interaction = interaction;
	}

	public Player player() {
		return player;
	}

	public City playerCurrentLocation() {
		return player().getLocation();
	}

	public abstract void perform();

	public abstract boolean canPerform();

	public boolean isOncePerTurn() {
		return false;
	}
}
