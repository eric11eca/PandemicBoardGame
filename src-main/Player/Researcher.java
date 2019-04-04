package Player;

import java.util.Random;

import Initialize.Board;

public class Researcher extends Player{


	public Researcher(Board gameBoard){
		super(gameBoard);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}


	public Researcher(Board gameBoard, Random random) {
		super(gameBoard, random);
	}
}
