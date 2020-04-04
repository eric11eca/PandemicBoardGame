package game.disease;

import java.util.Set;

import data.GameProperty;
import game.GameColor;
import game.GameState;

public class GameCubePool implements CubeData {

	private GameState game;
	private CubeData delegate;

	public GameCubePool(GameState game) {
		this.game = game;
		this.delegate = new CubeDataImpl();
	}

	public void initialize() {
		for (GameColor disease : GameColor.values()) {
			this.setDiseaseCubeCount(disease, getMaxDiseaseCubePerColor());
		}
	}

	public boolean isDiseaseEradicated(GameColor color) {
		return getDiseaseCubeCount(color) == getMaxDiseaseCubePerColor();
	}

	@Override
	public int getDiseaseCubeCount(GameColor color) {
		return delegate.getDiseaseCubeCount(color);
	}

	@Override
	public boolean hasDiseaseCube(GameColor color) {
		return delegate.hasDiseaseCube(color);
	}

	@Override
	public void addDiseaseCube(GameColor color, int count) {
		if (this.getDiseaseCubeCount(color) + count > getMaxDiseaseCubePerColor())
			throw new RuntimeException("Disease Cube Overflow: " + this.getDiseaseCubeCount(color) + count);
		delegate.addDiseaseCube(color, count);
	}

	@Override
	public void setDiseaseCubeCount(GameColor color, int count) {
		if (count > getMaxDiseaseCubePerColor())
			throw new RuntimeException("Disease Cube Overflow: " + count);
		delegate.setDiseaseCubeCount(color, count);
	}

	@Override
	public void removeAllDiseaseCube(GameColor color) {
		delegate.removeAllDiseaseCube(color);
		checkWin();
	}

	@Override
	public void removeDiseaseCube(GameColor color, int count) {
		if (this.getDiseaseCubeCount(color) - count < 0) {
			game.triggerLose();
			return;
		}
		delegate.removeDiseaseCube(color, count);
		checkWin();
	}

	private void checkWin() {
		for (GameColor c : GameColor.values()) {
			if (!this.isDiseaseEradicated(c))
				return;
		}
		game.triggerWin();
	}

	@Override
	public Set<GameColor> getExistingDiseases() {
		return delegate.getExistingDiseases();
	}

	private int getMaxDiseaseCubePerColor() {
		return GameProperty.getInstance().getInt("MAX_DISEASE_CUBE_PER_COLOR");
	}
}
