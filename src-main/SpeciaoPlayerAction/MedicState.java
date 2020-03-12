package SpeciaoPlayerAction;


import data.Board;
import player.PlayerData;

public class MedicState implements SpecialSkill{
	private Board board;
	private PlayerData medicData;
	
	public MedicState(PlayerData currentPlayerData) {
		board = Board.getInstance();
		medicData = currentPlayerData;
	}

	public void removeAllCubes() {
		for (String diseaseColor : medicData.location.diseaseCubes.keySet()) {
			if (board.curedDiseases.contains(diseaseColor)) {
				int numOfCube = medicData.location.diseaseCubes.get(diseaseColor);
				board.remainDiseaseCube.put(diseaseColor, board.remainDiseaseCube.get(diseaseColor) + numOfCube);			
				medicData.location.diseaseCubes.put(diseaseColor, 0);
				board.eradicatedColor.add(diseaseColor);
			}
		}
	}

	@Override
	public void applySkill() {
		removeAllCubes();
	}

}
