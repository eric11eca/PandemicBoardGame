package Player;

import java.util.Random;

import Initialize.Board;

public class Dispatcher extends Player {

	public Dispatcher(Board gameBoard) {
		super(gameBoard);
	}

	public Dispatcher(Board gameBoard, Random random) {
		super(gameBoard, random);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}

}
