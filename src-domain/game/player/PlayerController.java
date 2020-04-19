package game.player;

import java.util.List;
import java.util.Map;

import game.ActionType;
import game.cards.Card;
import game.city.City;
import game.player.action.Action;

/**
 * The PlayerController encapsulates a role and a player. The role is
 * represented by a set of actions. See {@link Action}
 */
public class PlayerController {
	private Player player;
	private Map<ActionType, Action> actionMap;

	public PlayerController(Player player, Map<ActionType, Action> actionMap) {
		super();
		this.player = player;
		this.actionMap = actionMap;
	}

	public void perform(ActionType actionType, Runnable completionCallback) {
		actionMap.get(actionType).perform(completionCallback);
	}

	public boolean canPerform(ActionType actionType, boolean alreadyPerformed) {
		return actionMap.containsKey(actionType) && actionMap.get(actionType).canPerform()
				&& !(alreadyPerformed && actionMap.get(actionType).isOncePerTurn());
	}

	public void givePlayerCards(List<Card> cards) {
		player.receiveCard(cards);
	}

	public int getHighestPopulationInHand() {
		return player.getHighestPopulationInHand();
	}

	public City getPlayerCity() {
		return player.getLocation();
	}

	public int getPlayerHandSize() {
		return getPlayerHand().size();
	}

	public List<Card> getPlayerHand() {
		return player.getFilteredHand(c -> true);
	}

	public PlayerRole getRole() {
		return player.getRole();
	}

}