package playerAction;

import initialize.Board;
import player.DiscoverCureNormal;
import player.PlayerData;
import player.StationBuilderNormal;
import player.TreatNormal;

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

	public void pickFromDiscardEventCard() {
		for (String name : board.discardEventCards) {
			if (name.equals(cardName)) {
				contingencyPlanner.specialEventCard = name;
				board.discardEventCards.remove(name);
				break;
			}
		}
	}

	@Override
	public void specialSkill() {
		pickFromDiscardEventCard();
	}
}
