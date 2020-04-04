package game.player.action;

import java.util.Set;

import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionDrive extends Action {
	private CitySet cities;

	public ActionDrive(CitySet cities, Player player, PlayerInteraction interaction) {
		super(player, interaction);
		this.cities = cities;
	}

	@Override
	public void perform() {
		Set<City> neighbors = cities.getCitiesSatisfying(player.getLocation()::isNeighboring);
		interaction.selectCityFrom(neighbors, this::performDriveAction);
	}

	@Override
	public boolean canPerform() {
		return true;
	}

	protected void performDriveAction(City destination) {
		player.setLocation(destination);
	}
}
