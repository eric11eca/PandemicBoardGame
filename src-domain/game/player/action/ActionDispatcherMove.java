package game.player.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionDispatcherMove extends Action {
	private Map<Player, Action> dispatchedActions;

	public ActionDispatcherMove(Map<Player, Action> dispatchedActions, PlayerInteraction interaction) {
		super(null, interaction);
		this.dispatchedActions = dispatchedActions;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectPlayerFrom(getPlayers(), I18n.format("action.dispatcher_move.select_player"),
				player -> dispatchedActions.get(player).perform(completionCallback));
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
