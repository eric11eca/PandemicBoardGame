package Player;

import Initialize.Board;

public class MedicAction implements SpecialSkill{
	private Board board;
	private PlayerData medicData;
	
	public MedicAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		medicData = currentPlayer;
		medicData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		medicData.buildStationModel = new StationBuilderNormal(medicData, board);
	}

	public void removeAllCubes() {
		Boolean allCured = true;
		for (String diseas : medicData.location.diseaseCubes.keySet()) {
			if (!board.curedDiseases.contains(diseas)) {
				allCured = false;
			}
		}
		medicData.location.diseaseCubes.clear();
	}

	@Override
	public void specialSkill() {
		removeAllCubes();
	}

}
