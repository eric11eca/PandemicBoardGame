package Player;

import Initialize.Board;

public class TreatMedic extends Treat {

	public TreatMedic(PlayerData medicData, Board gameBoard) {
		playerData = medicData;
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
