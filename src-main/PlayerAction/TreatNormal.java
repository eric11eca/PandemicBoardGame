package PlayerAction;

import data.Board;
import player.PlayerData;

public class TreatNormal extends Treat {

	public TreatNormal(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}

	@Override
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
