package Player;

import java.util.Random;

import Initialize.Board;

public class Scientist extends Player {

	public Scientist(Board gameBoard) {
		super(gameBoard);
	}

	public Scientist(Board gameBoard, Random random) {
		super(gameBoard, random);
		discoverCure = new DiscoverCureScientist(hand, gameBoard.curedDiseases);
	}
}
