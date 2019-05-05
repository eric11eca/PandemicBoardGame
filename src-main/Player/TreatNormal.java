package Player;

import Initialize.Board;

public class TreatNormal extends Treat {

	public TreatNormal(PlayerData playerData, Board gameBoard) {
		this.playerData = playerData;
		board = gameBoard;
	}
	
	public void treat(String diseaseColor) {
		remainCube = 0;
		super.treat(diseaseColor);
	}
}
