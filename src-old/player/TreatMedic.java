package player;

import data.Board;

public class TreatMedic extends Treat {

	public TreatMedic(PlayerData medicData, Board gameBoard) {
		playerData = medicData;
		board = gameBoard;
	}
	
	public void treat(String diseaseColor) {
		remainCube = 0;
		super.treat(diseaseColor);
	}
	
}
