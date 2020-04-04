package game.disease;

import java.util.Set;

import game.GameColor;

public interface CubeData {

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