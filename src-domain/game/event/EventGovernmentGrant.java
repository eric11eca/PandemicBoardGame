package game.event;

import game.city.CitySet;
import game.player.PlayerInteraction;

public class EventGovernmentGrant implements Event {
	/*
	 * Built a research station anywhere
	 */
	private CitySet cities;

	public EventGovernmentGrant(CitySet cities) {
		super();
		this.cities = cities;
	}

	@Override
	public void executeEvent(PlayerInteraction interaction) {
		interaction.selectCityFrom(cities.getCitiesSatisfying(c -> !c.hasResearchStation()),
				c -> c.buildResearchStation());
	}

}
