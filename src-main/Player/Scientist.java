package Player;

import Initialize.Board;

public class Scientist extends Player{

	public Scientist(Board gameBoard){
		super(gameBoard);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
		
	}
}
