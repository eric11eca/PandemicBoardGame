package Player;

import Initialize.Board;

public class ContingencyPlanner extends Player{
		
	public ContingencyPlanner(Board gameBoard) {
		super(gameBoard);
	}

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
	public void discoverCure(DiscoverCure discoverCure) {
		// TODO Auto-generated method stub
		
	}
	
	

}
