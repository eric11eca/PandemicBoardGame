package initialize.player;

import java.util.Map;
import java.util.function.Predicate;

import game.ActionType;
import game.city.City;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.special.QuarantineChecker;

public class QuarantineSpecialistFactory extends AbstractPlayerFactory {
	private Predicate<City> quarantineChecker;

	public QuarantineSpecialistFactory(int id) {
		super(id);
		quarantineChecker = c -> false;
	}

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.QUARANTINE_SPECIALIST;
	}

	@Override
	protected void addSpecialSkill(Player player, Map<ActionType, Action> actionMap) {
		this.quarantineChecker = new QuarantineChecker(player);
	}

	public Predicate<City> getQuarantineChecker() {
		return quarantineChecker;
	}

}
