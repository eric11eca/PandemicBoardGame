package player;

import initialize.Board;

public class TreatNormal extends Treat {

	public TreatNormal(PlayerData playerData, Board gameBoard) {
		this.playerData = playerData;
		board = gameBoard;
	}
	
	public void treat(String diseaseColor) {
		int numOfDiseaseCubes = playerData.location.diseaseCubes.get(diseaseColor);
		if (board.curedDiseases.contains(diseaseColor)) {
			remainCube = 0;
		} else {
			remainCube = numOfDiseaseCubes - 1;
		}
		super.treat(diseaseColor);
	}
}
