package game.event;

import java.util.List;

import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;

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
		interaction.selectPlayerFrom(players, p -> {
			interaction.selectCityFrom(cities.getCitiesSatisfying(c -> !p.getLocation().equals(c)), c -> {
				p.setLocation(c);
			});
		});
	}
}
