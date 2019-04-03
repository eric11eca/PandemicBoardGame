package Player;

import Initialize.Board;

public class Scientist extends Player {
	Board board;
	
	public Scientist(Board board) {
		this.board = board;
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}
}
