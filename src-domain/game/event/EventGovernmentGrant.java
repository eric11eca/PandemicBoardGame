package game.event;

import java.util.Optional;

import game.city.City;

public abstract class EventGovernmentGrant implements Event {
	/*
	 * Built a research station anywhere
	 */

	@Override
	public void executeEvent() {
		selectCity().ifPresent(c -> c.buildResearchStation());
	}

	protected abstract Optional<City> selectCity();
}
