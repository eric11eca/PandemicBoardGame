package Player;

import Initialize.Board;

public class ContingencyPlanner extends Player{
	Board board;

	public ContingencyPlanner(Board board) {
		this.board = board;
	}

	@Override
	public void removeAllCubes() {}

	@Override
	public void pickFromDiscardPlayerCard(String cardName) {
		for (String name : board.discardPlayerCard.keySet()) {
			if (name == cardName) {
				specialEventCard = board.discardPlayerCard.get(name);
				board.discardPlayerCard.remove(name);
				break;
			}
		} 
	}

	@Override
	public void buildResearchStation() {}

	@Override
	public void moveToAnotherCity(String cityName) {}
	
	
}
