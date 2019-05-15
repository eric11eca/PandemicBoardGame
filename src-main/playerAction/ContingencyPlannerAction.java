package playerAction;


import data.Board;
import player.PlayerData;


public class ContingencyPlannerAction implements SpecialSkill{
	public String cardName;
	private Board board;
	private PlayerData contingencyPlanner;
	
	public ContingencyPlannerAction(Board gameBoard, PlayerData currentPlayerData) {
		board = gameBoard;
		contingencyPlanner = currentPlayerData;
	}

	public void pickFromDiscardEventCard() {
		for (String name : board.discardEventCards) {
			if (name.equals(cardName)) {
				contingencyPlanner.roleCard = name;
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
