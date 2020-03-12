package gameAction;

import java.util.ArrayList;
import java.util.List;
import data.Board;

public class GameAction {
	Board board;
	private List<Command> oneTurnCommands;

	public GameAction() {
		board = Board.getInstance();
		oneTurnCommands = new ArrayList<>();
		initCommandList();
	}
	
	private void initCommandList() {
		for(int i = 0 ; i < 4; i++) {
			oneTurnCommands.add(new NoCommand());
		}
	}
	
	public void setCommands(int slot, Command command) {
		oneTurnCommands.add(slot, command);
	}

	public void drawTwoPlayerCards() {
		oneTurnCommands.get(1).execute();
	}
	
	public void doAction() {
		oneTurnCommands.get(0).execute();
	}

	public void infection() {
		oneTurnCommands.get(2).execute();
	}

}