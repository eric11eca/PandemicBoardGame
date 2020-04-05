package game.player.special;

import java.util.function.Predicate;

import game.city.City;
import game.player.Player;

/**
 * Implements the Quarantine Specialist skill. See {@link City}
 */
public class QuarantineChecker implements Predicate<City> {
	private Player quarantineSpecialist;

	public QuarantineChecker(Player quarantineSpecialist) {
		super();
		this.quarantineSpecialist = quarantineSpecialist;
	}

	@Override
	public boolean test(City t) {
		return quarantineSpecialist.getLocation().equals(t) || quarantineSpecialist.getLocation().isNeighboring(t);
	}

}
