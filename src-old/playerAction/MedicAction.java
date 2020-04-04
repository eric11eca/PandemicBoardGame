package playerAction;

import data.Board;
import game.GameColor;
import player.PlayerData;

public class MedicAction implements SpecialSkill {
	private Board board;
	private PlayerData medicData;

	public MedicAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		medicData = currentPlayerData;
	}

	public void removeAllCubes() {
		for (GameColor diseaseColor : GameColor.values()) {
			if (board.curedDiseases.contains(diseaseColor.compatibility_ColorString)) {
				medicData.location.eradicateDisease(diseaseColor);
			}
		}
	}

	@Override
	public void useSpecialSkill() {
		removeAllCubes();
	}

}
