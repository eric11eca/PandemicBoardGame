package Player;

import Initialize.Board;

public class Medic extends Player {

	public Medic(Board gameBoard) {
		super(gameBoard);
		discoverCure = new DiscoverCureNormal(gameBoard.curedDiseases);
		buildStationModel = new StationBuilderNormal(this, gameBoard);
	}

	public void removeAllCubes() {
		Boolean allCured = true;
		for (String diseas : location.diseaseCubes.keySet()) {
			if (!board.curedDiseases.contains(diseas)) {
				allCured = false;
			}
		}
		if (!allCured) {
			consumeAction();
		}
		location.diseaseCubes.clear();
	}

}
