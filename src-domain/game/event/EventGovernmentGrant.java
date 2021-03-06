package game.event;

import game.city.CitySet;
import game.player.PlayerInteraction;
import lang.I18n;

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
				"event.government_grant.select_city", c -> c.buildResearchStation());
	}

	@Override
	public String getName() {
		return I18n.format("event.grant");
	}

}
