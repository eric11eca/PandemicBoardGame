package game.player.special;

import java.util.Set;

import game.GameColor;
import game.city.City;
import game.player.AbstractPlayerDecorator;
import game.player.Player;

public class Medic extends AbstractPlayerDecorator {
	private Set<GameColor> curedDiseases;

	public Medic(Player delegate, Set<GameColor> curedDiseases) {
		super(delegate);
		this.curedDiseases = curedDiseases;
	}

	@Override
	public void setLocation(City destination) {
		super.setLocation(destination);
		curedDiseases.forEach(destination::eradicateDisease);
	}

}
