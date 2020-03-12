package PlayerAction;

import data.Board;
import data.City;
import player.PlayerData;

public class ShuttleFlight extends Mobility{
	public ShuttleFlight(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}
	
	@Override
	public boolean checkConditions() {
		if (playerData.location.researchStation) {
			if (destination.researchStation) {
				return true;
			} 
		}
		return false;
	}

	@Override
	public void setDestination(City dest) {
		destination = board.cities.get(cityCard.cardName);
	}
}
