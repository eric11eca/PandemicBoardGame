package PlayerAction;

import cards.PlayerCard;
import data.Board;
import data.City;
import player.PlayerData;

public abstract class Mobility {
	public Board board;
	public PlayerData playerData;
	public PlayerCard cityCard;
	public City destination;
	public boolean drive;
	
	public void move(City destination) {
		if (drive) {
			setDestination(destination);
			if(checkConditions()) {
				moveToCity();
				discardCardAfterMove();
			}
		} else {
			if(checkConditions()) {
				setDestination(destination);
				moveToCity();
				discardCardAfterMove();
			}
		}
	}
	public abstract boolean checkConditions();
	public abstract void setDestination(City destination);
	public void discardCardAfterMove() {}
	
	public void moveToCity() {
		if(board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData; 
			pawnData.location = destination;
			destination.currentRoles.add(pawnData.role);
		} else {
			playerData.location = destination;
			destination.currentRoles.add(this.playerData.role);
		}
	}
}
