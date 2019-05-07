package cards;

import java.util.List;

import Initialize.Board;
import javafx.util.Pair;

public class ForecastEvent implements EventCard{
	private Board board;
	
	public ForecastEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	public void reviewCard() {
		for(int i = 0; i < 6; i++) {
			String name = board.validInfectionCards.get(i);
			String color = board.cities.get(name).color;
			board.infectionCardForecast.put(name, color);
		}
	}	
	
	public void arrangeCard() {
		List<Pair<String, Integer>> instruction = board.rearrangeInstruction;
		for(int i = 0; i < instruction.size(); i++) {
			String infection = instruction.get(i).getKey();
			int newAddress = instruction.get(i).getValue();
			board.validInfectionCards.add(newAddress, infection);
		}
	}

	@Override
	public void executeEvent() {
		reviewCard();
		arrangeCard();
	}
}
