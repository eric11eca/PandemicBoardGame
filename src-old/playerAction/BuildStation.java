package playerAction;

import data.Board;
import player.Player;

public class BuildStation extends PlayerAction{

	public BuildStation(Board board, Player player) {
		super(board, player);
	}

	@Override
	public boolean executeAction() {
		player.playerData.buildStationModel.buildStation();
		player.consumeAction();
		return false;
	}
}
