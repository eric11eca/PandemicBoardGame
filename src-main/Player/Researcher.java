package Player;

import java.util.Random;

import Initialize.Board;

public class Researcher extends Player{


	public Researcher(Board gameBoard){
		super(gameBoard);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		// TODO Auto-generated method stub
		
	}


	public Researcher(Board gameBoard, Random random) {
		super(gameBoard, random);
	}
}
