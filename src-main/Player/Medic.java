package Player;


import java.util.Random;

import Initialize.Board;

public class Medic extends Player {

	public Medic(Board gameBoard) {
		super(gameBoard);
	}

	public Medic(Board gameBoard, Random random) {
		super(gameBoard, random);
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
