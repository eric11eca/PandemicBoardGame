package game.cards.event;

import java.util.Optional;

import game.City;

public abstract class EventGovernmentGrant implements CardEvent {
	/*
	 * Built a research station anywhere
	 */

	@Override
	public void executeEvent() {
		selectCity().ifPresent(c -> c.buildResearchStation());
	}

	protected abstract Optional<City> selectCity();
}
