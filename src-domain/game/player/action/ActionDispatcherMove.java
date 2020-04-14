package game.player.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.player.Player;
import game.player.PlayerInteraction;

public class ActionDispatcherMove extends Action {
	private Map<Player, Action> dispatchedActions;

	public ActionDispatcherMove(Map<Player, Action> dispatchedActions, PlayerInteraction interaction) {
		super(null, interaction);
		dispatchedActions = new HashMap<>();
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(getPlayers(), player -> dispatchedActions.get(player).perform(completionCallback));
	}

	protected List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		dispatchedActions.forEach((player, action) -> {
			if (action.canPerform())
				list.add(player);
		});
		return list;
	}

	@Override
	public boolean canPerform() {
		for (Action dispatchedAction : dispatchedActions.values()) {
			if (dispatchedAction.canPerform())
				return true;
		}
		return false;
	}

}
