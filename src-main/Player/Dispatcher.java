package Player;

import Initialize.Board;

public class Dispatcher extends Player {
	
	public Dispatcher(Board gameBoard){
		super(gameBoard);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}

}
