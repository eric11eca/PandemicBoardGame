package game;

import data.GameColor;
import game.city.CubeData;

public class Game {
	private static Game instance;

	public static final int MAX_CUBE_IN_POOL = 24;

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	private CubeData diseaseCubes;
	private boolean lost;
	private boolean won;
	private int outbreakMark;

	private Game() {
		diseaseCubes = new CubeData();
	}

	public void triggerWin() {
		won = true;
	}

	public void triggerLose() {
		lost = true;
	}

	public boolean isLost() {
		return lost;
	}

	public boolean isWon() {
		return won;
	}

	public void putCubeToPool(GameColor color, int count) {
		diseaseCubes.addDiseaseCube(color, count);
	}

	public void takeCubeFromPool(GameColor color, int count) {
		if (diseaseCubes.getDiseaseCubeCount(color) < count) {
			triggerLose();
			return;
		}
		diseaseCubes.removeDiseaseCube(color, count);
	}

	public void moveOutbreakMarkForward() {
		outbreakMark += 1;
		if (outbreakMark == 8) {
			triggerLose();
		}
	}

	public boolean isDiseaseEradicated(GameColor color) {
		return diseaseCubes.getDiseaseCubeCount(color) == MAX_CUBE_IN_POOL;
	}

	public void initializeDiseaseCubes() {
		for (GameColor color : GameColor.values())
			diseaseCubes.setDiseaseCubeCount(color, MAX_CUBE_IN_POOL);
	}

}
