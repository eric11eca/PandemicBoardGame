package PlayerAction;

import data.Board;
import data.City;
import player.PlayerData;

public class DirectFlight extends Mobility{
	
	public DirectFlight(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}

	@Override
	public boolean checkConditions() {
		if (cityCard.cardType == Board.CardType.CITYCARD) {
			return true;
		}
		return false;
	}	
	
	@Override 
	public void discardCardAfterMove() {
		board.cardToBeDiscard.add(playerData.location.cityName);
	}

	@Override
	public void setDestination(City dest) {
		destination = board.cities.get(cityCard.cardName);
	}
}
