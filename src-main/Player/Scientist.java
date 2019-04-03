package Player;

import Initialize.Board;

public class Scientist extends Player{
	Board board;
	
	public Scientist(Board board) {
		this.board = board;
	}

	public void discoverCure(String disease) {
		int count = 0;
		for(String cardName : hand.keySet()) {
			if(hand.get(cardName).color == disease) {
				count++;
			}
		}
		if(count >= 4) {
			board.curedDiseases.add(disease);
		}
	}
}
