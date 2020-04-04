package game.player.action;

import java.util.Set;

import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionShuttleFlight extends Action {

	private CitySet cities;

	public ActionShuttleFlight(CitySet cities, Player player, PlayerInteraction interaction) {
		super(player, interaction);
		this.cities = cities;
	}

	@Override
	public void perform() {
		interaction.selectCityFrom(getCitiesWithResearchStationExceptCurrent(), this::performShuttleFlightAction);
	}

	@Override
	public boolean canPerform() {
		boolean currentCityHasStation = playerCurrentLocation().hasResearchStation();
		boolean otherStationCityExist = !getCitiesWithResearchStationExceptCurrent().isEmpty();
		return currentCityHasStation && otherStationCityExist;
	}

	protected Set<City> getCitiesWithResearchStationExceptCurrent() {
		return cities.getCitiesSatisfying(c -> c.hasResearchStation() && !c.equals(playerCurrentLocation()));
	}

	protected void performShuttleFlightAction(City destination) {
		if (!playerCurrentLocation().hasResearchStation() || !destination.hasResearchStation())
			throw new RuntimeException("Not both cities have stations");
		player().setLocation(destination);
	}

}
