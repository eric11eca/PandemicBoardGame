package game.event;

import java.util.List;

import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class EventAirlift implements Event {
	/*
	 * Move any pawn to any city. Get permission before moving other player's pawn.
	 */
	private List<Player> players;
	private CitySet cities;

	public EventAirlift(List<Player> players, CitySet cities) {
		super();
		this.players = players;
		this.cities = cities;
	}

	@Override
	public void executeEvent(PlayerInteraction interaction) {
		interaction.selectPlayerFrom(players, "event.airlift.select_player", p -> {
			interaction.selectCityFrom(cities.getCitiesSatisfying(c -> !p.getLocation().equals(c)),
					"event.airlift.select_destination", c -> {
						p.setLocation(c);
					});
		});
	}

	@Override
	public String getName() {
		return I18n.format("event.airlift");
	}
}
