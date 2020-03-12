package gameAction;

import cardActions.InfectionCardAction;
import data.Board;

public class InfectionCommand implements Command{
	Board board;
	InfectionCardAction infect;
	
	public InfectionCommand(InfectionCardAction infectAction) {
		board = Board.getInstance();
		infect = infectAction;
	}

	@Override
	public void execute() {
		if (board.inQueitNight) {
			board.inQueitNight = false;
			return;
		}
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infect.drawOneInfectionCard();
		}
	}
}
