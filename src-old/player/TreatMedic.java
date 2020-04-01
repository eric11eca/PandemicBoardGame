package player;

import data.Board;
import data.GameColor;
import game.city.City;

public class TreatMedic implements Treat {

	@Override
	public void treat(City city, GameColor diseaseColor) {
		city.eradicateDisease(diseaseColor);
	}

}
