package Player;

import Initialize.Board;

public class ContingencyPlannerAction implements SpecialSkill{
	public String cardName;
	private Board board;
	private PlayerData contingencyPlanner;
	
	public ContingencyPlannerAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		contingencyPlanner = currentPlayerData;
		contingencyPlanner.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		contingencyPlanner.buildStationModel = new StationBuilderNormal(contingencyPlanner, board);
		contingencyPlanner.treatAction = new TreatNormal(contingencyPlanner, board);
	}

	public void pickFromDiscardPlayerCard() {
		for (String name : board.discardPlayerCard.keySet()) {
			if (name.equals(cardName)) {
				contingencyPlanner.specialEventCard = board.discardPlayerCard.get(name);
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
