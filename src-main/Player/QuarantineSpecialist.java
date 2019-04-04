package Player;

import java.util.Random;

import Initialize.Board;

public class QuarantineSpecialist extends Player{


	public QuarantineSpecialist(Board gameBoard){
		super(gameBoard);
	}

	@Override
	public void discoverCure(DiscoverCure discoverCure) {
		discoverCure.discoverCure();
	}

	public QuarantineSpecialist(Board gameBoard, Random random) {
		super(gameBoard, random);
	}

}
