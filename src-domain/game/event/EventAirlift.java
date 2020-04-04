package game.event;

import java.util.Optional;

import game.city.City;
import game.player.Player;

public abstract class EventAirlift implements Event {
	/*
	 * Move any pawn to any city. Get permission before moving other player's pawn.
	 */
	@Override
	public void executeEvent() {
		selectPlayer().ifPresent(p -> selectCity(p).ifPresent(c -> p.setLocation(c)));
	}

	protected abstract Optional<Player> selectPlayer();

	protected abstract Optional<City> selectCity(Player playerToMove);
}
