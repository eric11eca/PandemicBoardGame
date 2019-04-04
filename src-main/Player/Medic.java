package Player;

import Initialize.Board;

public class Medic extends Player{
	
	public Medic(Board gameBoard) {
		super(gameBoard);
	}

	public void removeAllCubes() {
		Boolean allCured = true;
		for(String diseas : location.diseaseCubes.keySet()) {
			if(!board.curedDiseases.contains(diseas)) {
				allCured = false;
			}
		}	
		if(!allCured) {
			action--;
		}
		location.diseaseCubes.clear();
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}
}
