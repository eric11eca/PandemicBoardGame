package game.cards.event;

import java.util.Optional;

import game.City;
import game.player.Player;

public abstract class EventAirlift implements CardEvent {
	/*
	 * Move any pawn to any city. Get permission before moving other player's pawn.
	 */
	@Override
	public void executeEvent() {
		selectPlayer().ifPresent(p -> selectCity(p).ifPresent(c -> p.moveTo(c)));
	}

	protected abstract Optional<Player> selectPlayer();

	protected abstract Optional<City> selectCity(Player playerToMove);
}
