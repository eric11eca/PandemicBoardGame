package game.event;

import game.player.PlayerInteraction;

public interface Event {
	void executeEvent(PlayerInteraction interaction);
}
