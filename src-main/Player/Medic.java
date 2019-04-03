package Player;

import Initialize.Board;

public class Medic extends Player{
	Board board;
	public Medic(Board board) {
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

	@Override
	public void pickFromDiscardPlayerCard(String cardName) {}

	@Override
	public void buildResearchStation() {}

	@Override
	public void moveToAnotherCity(String cityName) {}
	
	
}
