package game.disease;

import java.util.Set;

import game.GameColor;

public class CityCubeData implements CubeData {
	private CubeData delegate;
	private CubeData gamePool;

	public CityCubeData(CubeData gamePool) {
		this.gamePool = gamePool;
		this.delegate = new CubeDataImpl();
	}

	public int getDiseaseCubeCount(GameColor color) {
		return delegate.getDiseaseCubeCount(color);
	}

	public boolean hasDiseaseCube(GameColor color) {
		return delegate.hasDiseaseCube(color);
	}

	public void addDiseaseCube(GameColor color, int count) {
		gamePool.removeDiseaseCube(color, count);
		delegate.addDiseaseCube(color, count);
	}

	public void setDiseaseCubeCount(GameColor color, int count) {
		removeAllDiseaseCube(color);
		addDiseaseCube(color, count);
	}

	public void removeAllDiseaseCube(GameColor color) {
		this.removeDiseaseCube(color, this.getDiseaseCubeCount(color));
	}

	public void removeDiseaseCube(GameColor color, int count) {
		delegate.removeDiseaseCube(color, count);
		gamePool.addDiseaseCube(color, count);
	}

	public Set<GameColor> getExistingDiseases() {
		return delegate.getExistingDiseases();
	}
}
