package Card;

import java.util.List;

import Initialize.Board;
import javafx.util.Pair;

public class ForecastEvent {
	private Board board;
	
	public ForecastEvent(Board gameBoard) {
		board = gameBoard;
	}
	
	private void reviewCard() {
		for(int i = 0; i < 6; i++) {
			String name = board.validInfectionCard.get(i);
			String color = board.cities.get(name).color;
			board.infectionCardForecast.put(name, color);
		}
	}	
	
	private void arrangeCard() {
		List<Pair<String, Integer>> instruction = board.rearrangeInstruction;
		for(int i = 0; i < instruction.size(); i++) {
			String infection = instruction.get(i).getKey();
			int newAddress = instruction.get(i).getValue();
			board.validInfectionCard.add(newAddress, infection);
		}
	}
	
	public void forecast() {
		reviewCard();
		arrangeCard();
	}
}
