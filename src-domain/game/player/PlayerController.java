package game.player;

import java.util.List;
import java.util.Map;

import game.ActionType;
import game.cards.Card;
import game.player.action.Action;

public class PlayerController {
	private Player player;
	private Map<ActionType, Action> actionMap;

	public PlayerController(Player player, Map<ActionType, Action> actionMap) {
		super();
		this.player = player;
		this.actionMap = actionMap;
	}

	public void perform(ActionType actionType) {
		actionMap.get(actionType).perform();
	}

	public boolean canPerform(ActionType actionType) {
		return actionMap.containsKey(actionType) && actionMap.get(actionType).canPerform();
	}

	public void givePlayerCards(List<Card> cards) {
		player.receiveCard(cards);
	}

	public int getHighestPopulationInHand() {
		return player.getHighestPopulationInHand();
	}
}