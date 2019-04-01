package Player;

import java.util.ArrayList;

import Initialize.Board;
import Initialize.City;

public class Medic extends Player{
	Board board;
	public Medic(Board board) {
		location = new City();
		hand = new ArrayList<>();
		action  = 4;
		this.board = board;
	}

	@Override
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
