package SpeciaoPlayerAction;


import data.Board;
import player.PlayerData;


public class ContingencyPlannerState implements SpecialSkill{
	public String cardName;
	private Board board;
	private PlayerData contingencyPlanner;
	
	public ContingencyPlannerState(PlayerData currentPlayerData) {
		board = Board.getInstance();
		contingencyPlanner = currentPlayerData;
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
	public void applySkill() {
		pickFromDiscardEventCard();
	}
}
