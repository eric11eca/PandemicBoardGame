package playerAction;


import data.Board;
import player.PlayerData;

public class MedicAction implements SpecialSkill{
	private Board board;
	private PlayerData medicData;
	
	public MedicAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
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
	public void useSpecialSkill() {
		removeAllCubes();
	}

}
