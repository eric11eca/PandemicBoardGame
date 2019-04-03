package Player;

import Initialize.Board;

public class Medic extends Player{
	Board board;
	public Medic(Board board) {
		this.board = board;
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
}
