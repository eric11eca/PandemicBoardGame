package gameAction;

import java.util.HashMap;
import java.util.Map;

import data.Board;

public class ActionCommand implements Command{
	private Board board;
	private Command command;
	private Map<Board.ActionName, Command> actionCommands;

	public ActionCommand() {
		board = Board.getInstance();
		actionCommands = new HashMap<>();
	}
	
	public void addActionCommand(Board.ActionName actionName, Command command) {
		actionCommands.put(actionName, command);
	}

	@Override
	public void execute() {
		boolean isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		command = actionCommands.get(board.actionName);
		System.out.println(board.actionName);
		command.execute();
		if(isMedic && board.doesChangeLocation) {
			board.currentPlayer.state.useSpecialSkill();
		}
	}
}
