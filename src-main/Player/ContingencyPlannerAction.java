package Player;

import Initialize.Board;

public class ContingencyPlannerAction implements SpecialSkill{
	public String cardName;
	private Board board;
	private PlayerData playerData;
	
	public ContingencyPlannerAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		playerData = currentPlayer;
		playerData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		playerData.buildStationModel = new StationBuilderNormal(playerData, board);
	}

	public void pickFromDiscardPlayerCard() {
		for (String name : board.discardPlayerCard.keySet()) {
			if (name.equals(cardName)) {
				playerData.specialEventCard = board.discardPlayerCard.get(name);
				board.discardPlayerCard.remove(name);
				break;
			}
		}
	}

	@Override
	public void specialSkill() {
		pickFromDiscardPlayerCard();
	}
}
