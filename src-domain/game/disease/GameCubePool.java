package game.disease;

import java.util.Set;

import data.GameColor;
import data.GameProperty;
import game.Game;

public class GameCubePool implements CubeData {
	private static final int MAX_DISEASE_CUBE_PER_COLOR = GameProperty.getInstance()
			.getInt("MAX_DISEASE_CUBE_PER_COLOR");
	private Game game;
	private CubeData delegate;

	public GameCubePool(Game game) {
		this.game = game;
		this.delegate = new CubeDataImpl();
	}

	public void initialize() {
		for (GameColor disease : GameColor.values()) {
			this.setDiseaseCubeCount(disease, MAX_DISEASE_CUBE_PER_COLOR);
		}
	}

	public boolean isDiseaseEradicated(GameColor color) {
		return getDiseaseCubeCount(color) == MAX_DISEASE_CUBE_PER_COLOR;
	}

	public int getDiseaseCubeCount(GameColor color) {
		return delegate.getDiseaseCubeCount(color);
	}

	public boolean hasDiseaseCube(GameColor color) {
		return delegate.hasDiseaseCube(color);
	}

	public void addDiseaseCube(GameColor color, int count) {
		if (this.getDiseaseCubeCount(color) + count > MAX_DISEASE_CUBE_PER_COLOR)
			throw new RuntimeException("Disease Cube Overflow: " + this.getDiseaseCubeCount(color) + count);
		delegate.addDiseaseCube(color, count);
	}

	public void setDiseaseCubeCount(GameColor color, int count) {
		if (count > MAX_DISEASE_CUBE_PER_COLOR)
			throw new RuntimeException("Disease Cube Overflow: " + count);
		delegate.setDiseaseCubeCount(color, count);
	}

	public void removeAllDiseaseCube(GameColor color) {
		delegate.removeAllDiseaseCube(color);
	}

	public void removeDiseaseCube(GameColor color, int count) {
		if (this.getDiseaseCubeCount(color) - count < 0) {
			game.triggerLose();
			return;
		}
		delegate.removeDiseaseCube(color, count);
	}

	public Set<GameColor> getExistingDiseases() {
		return delegate.getExistingDiseases();
	}
}
