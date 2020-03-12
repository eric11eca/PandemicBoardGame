package PlayerAction;

import data.Board;
import data.City;
import player.PlayerData;

public class CharterFlight extends Mobility{
	public CharterFlight(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}
	
	@Override 
	public void discardCardAfterMove() {
		board.cardToBeDiscard.add(playerData.location.cityName);
	}

	@Override
	public boolean checkConditions() {
		return false;
	}

	@Override
	public void setDestination(City destination) {
		String destinationName = board.cityCardNameCharter;
		destination = board.cities.get(destinationName);
	}
}
