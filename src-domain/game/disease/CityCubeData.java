package game.disease;

import java.util.Set;

import game.GameColor;

/**
 * A semi-decorator that represents cubes placed on a city. It is linked with
 * {@link GameCubePool} to automatically transfer cubes and trigger lose
 * condition.
 */
public class CityCubeData implements CubeData {
	private CubeData delegate;
	private CubeData gamePool;

	public CityCubeData(CubeData gamePool) {
		this.gamePool = gamePool;
		this.delegate = new CubeDataImpl();
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
		gamePool.removeDiseaseCube(color, count);
		delegate.addDiseaseCube(color, count);
	}

	@Override
	public void setDiseaseCubeCount(GameColor color, int count) {
		removeAllDiseaseCube(color);
		addDiseaseCube(color, count);
	}

	@Override
	public void removeAllDiseaseCube(GameColor color) {
		this.removeDiseaseCube(color, this.getDiseaseCubeCount(color));
	}

	@Override
	public void removeDiseaseCube(GameColor color, int count) {
		delegate.removeDiseaseCube(color, count);
		gamePool.addDiseaseCube(color, count);
	}

	@Override
	public Set<GameColor> getExistingDiseases() {
		return delegate.getExistingDiseases();
	}
}
