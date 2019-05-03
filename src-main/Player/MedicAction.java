package Player;

import Initialize.Board;

public class MedicAction implements SpecialSkill{
	private Board board;
	private PlayerData playerData;
	private Player player;
	
	public MedicAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		playerData = currentPlayer;
		playerData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		playerData.buildStationModel = new StationBuilderNormal(playerData, board);
		player = new Player(board, playerData);
	}

	public void removeAllCubes() {
		Boolean allCured = true;
		for (String diseas : playerData.location.diseaseCubes.keySet()) {
			if (!board.curedDiseases.contains(diseas)) {
				allCured = false;
			}
		}
		if (!allCured) {
			player.consumeAction();
		}
		playerData.location.diseaseCubes.clear();
	}

	@Override
	public void specialSkill() {
		removeAllCubes();
	}

}
