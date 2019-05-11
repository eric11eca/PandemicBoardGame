package cards;

import java.util.List;

import initialize.Board;
import javafx.util.Pair;

public class ForecastEvent implements EventCard{
	private Board board;
	
	public ForecastEvent(Board gameBoard) {
		board = gameBoard;
	}	
	
	public void arrangeCard() {
		List<String> instruction = board.rearrangeInstruction;
		for(int i = 0; i < 6; i++) {
			board.validInfectionCards.add(instruction.get(i));
		}
	}

	@Override
	public void executeEvent() {
		arrangeCard();
	}
}
