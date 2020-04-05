package game.disease;

import java.util.Set;

import game.GameColor;

/**
 * The abstract of cube data, which is a data structure that represent the
 * physical disease cubes in the game.
 */
public interface CubeData {
	// All methods are self-explanatory
	int getDiseaseCubeCount(GameColor color);

	boolean hasDiseaseCube(GameColor color);

	default void addDiseaseCube(GameColor color) {
		addDiseaseCube(color, 1);
	}

	void addDiseaseCube(GameColor color, int count);

	void setDiseaseCubeCount(GameColor color, int count);

	default void removeDiseaseCube(GameColor color) {
		removeDiseaseCube(color, 1);
	}

	void removeAllDiseaseCube(GameColor color);

	void removeDiseaseCube(GameColor color, int count);

	Set<GameColor> getExistingDiseases();

}