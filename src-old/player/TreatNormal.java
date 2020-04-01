package player;

import data.Board;
import data.GameColor;
import game.city.City;

public class TreatNormal implements Treat {
	private Board board;

	public TreatNormal(Board gameBoard) {
//		this.playerData = playerData;
		board = gameBoard;
	}
//
//	public void treat(String diseaseColor) {// Extremely Bad Inheritance. Is this even necessary?
//		int numOfDiseaseCubes = playerData.location.diseaseCubes.get(diseaseColor);
//		if (board.curedDiseases.contains(diseaseColor)) {
//			remainCube = 0;
//		} else {
//			remainCube = numOfDiseaseCubes - 1;
//		}
//		super.treat(diseaseColor);
//	}

	@Override
	public void treat(City city, GameColor diseaseColor) {
		if (board.curedDiseases.contains(diseaseColor.compatibility_ColorString)) {
			city.eradicateDisease(diseaseColor);
		} else {
			city.treatDisease(diseaseColor);
		}
	}
}
