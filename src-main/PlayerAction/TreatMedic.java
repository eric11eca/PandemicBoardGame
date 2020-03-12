package PlayerAction;

import data.Board;
import player.PlayerData;

public class TreatMedic extends Treat {
	
	public TreatMedic(PlayerData data) {
		board = Board.getInstance();
		playerData = data;
	}

	@Override
	public void treat(String diseaseColor) {
		remainCube = 0;
		super.treat(diseaseColor);
	}
	
}
