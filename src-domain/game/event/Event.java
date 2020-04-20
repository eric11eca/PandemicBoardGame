package game.event;

import game.player.PlayerInteraction;

/**
 * The event interface
 */
public interface Event {
	void executeEvent(PlayerInteraction interaction);

	default String getName() {
		return getClass().getSimpleName();
	}
}
