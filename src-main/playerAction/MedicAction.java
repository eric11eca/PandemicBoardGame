package playerAction;

import initialize.Board;
import player.DiscoverCureNormal;
import player.PlayerData;
import player.StationBuilderNormal;
import player.TreatMedic;

public class MedicAction implements SpecialSkill{
	private Board board;
	private PlayerData medicData;
	
	public MedicAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		medicData = currentPlayerData;
		medicData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		medicData.buildStationModel = new StationBuilderNormal(medicData, board);
		medicData.treatAction = new TreatMedic(currentPlayerData, board);
	}

	public void removeAllCubes() {
		for (String diseaseColor : medicData.location.diseaseCubes.keySet()) {
			if (board.curedDiseases.contains(diseaseColor)) {
				int numOfCube = medicData.location.diseaseCubes.get(diseaseColor);
				board.remainDiseaseCube.put(diseaseColor, board.remainDiseaseCube.get(diseaseColor) + numOfCube);			
				medicData.location.diseaseCubes.put(diseaseColor, 0);
			}
		}
	}

	@Override
	public void specialSkill() {
		removeAllCubes();
	}

}
