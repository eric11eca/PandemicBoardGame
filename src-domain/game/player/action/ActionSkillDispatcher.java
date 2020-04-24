package game.player.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionSkillDispatcher extends Action {
	private List<Player> players;

	public ActionSkillDispatcher(List<Player> players, PlayerInteraction interaction) {
		super(null, interaction);
		this.players = players;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(getMovablePlayers(), "action.skill.dispatcher.select_player", toMove -> {
			interaction.selectCityFrom(getMovableCities(toMove), "action.skill.dispatcher.select_city", toMoveTo -> {
				this.performSpecialSkill(toMove, toMoveTo, completionCallback);
			});
		});
	}

	@Override
	public boolean canPerform() {
		return !getMovablePlayers().isEmpty();
	}

	protected List<Player> getMovablePlayers() {
		List<Player> validPlayers = new ArrayList<>();
		for (Player p : players) {
			if (!getMovableCities(p).isEmpty()) {
				validPlayers.add(p);
			}
		}
		return validPlayers;
	}

	protected Set<City> getMovableCities(Player toMove) {
		Set<City> playerLocations = new HashSet<>();
		for (Player p : players) {
			if (!p.getLocation().equals(toMove.getLocation()))
				playerLocations.add(p.getLocation());
		}
		return playerLocations;
	}

	protected void performSpecialSkill(Player toMove, City toMoveTo, Runnable completionCallback) {
		assert !toMove.getLocation().equals(toMoveTo);
		assert getMovableCities(toMove).contains(toMoveTo);
		toMove.setLocation(toMoveTo);
		completionCallback.run();
	}

}
