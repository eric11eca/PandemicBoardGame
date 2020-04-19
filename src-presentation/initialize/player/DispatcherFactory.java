package initialize.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.ActionType;
import game.player.Player;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionCharterFlight;
import game.player.action.ActionDirectFlight;
import game.player.action.ActionDispatcherMove;
import game.player.action.ActionDrive;
import game.player.action.ActionShuttleFlight;
import game.player.action.ActionSkillDispatcher;
import game.player.special.DispatchedPlayer;

public class DispatcherFactory extends AbstractPlayerFactory {

	@Override
	protected PlayerRole getRole() {
		return PlayerRole.DISPATCHER;
	}

	@Override
	protected void addSpecialSkill(Player dispatcher, Map<ActionType, Action> actionMap) {
		Map<Player, Action> dispatchedDriveActions = new HashMap<>();
		Map<Player, Action> dispatchedDirectFlightActions = new HashMap<>();
		Map<Player, Action> dispatchedCharterFlightActions = new HashMap<>();
		Map<Player, Action> dispatchedShuttleFlightActions = new HashMap<>();
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.addAll(players);
		allPlayers.add(dispatcher);
		allPlayers.forEach(player -> {
			Player dispatchedPlayer = new DispatchedPlayer(dispatcher, player);
			dispatchedDriveActions.put(player, new ActionDrive(citySet, dispatchedPlayer, interaction));
			dispatchedDirectFlightActions.put(player, new ActionDirectFlight(dispatchedPlayer, interaction));
			dispatchedCharterFlightActions.put(player, new ActionCharterFlight(citySet, dispatchedPlayer, interaction));
			dispatchedShuttleFlightActions.put(player, new ActionShuttleFlight(citySet, dispatchedPlayer, interaction));
		});
		actionMap.put(ActionType.DRIVE, new ActionDispatcherMove(dispatchedDriveActions, interaction));
		actionMap.put(ActionType.DIRECT_FLIGHT, new ActionDispatcherMove(dispatchedDirectFlightActions, interaction));
		actionMap.put(ActionType.CHARTER_FLIGHT, new ActionDispatcherMove(dispatchedCharterFlightActions, interaction));
		actionMap.put(ActionType.SHUTTLE_FLIGHT, new ActionDispatcherMove(dispatchedShuttleFlightActions, interaction));

		actionMap.put(ActionType.SPECIAL_SKILL, new ActionSkillDispatcher(allPlayers, interaction));
	}

}
