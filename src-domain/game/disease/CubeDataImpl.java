package game.disease;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import data.GameColor;

public class CubeDataImpl implements CubeData {
	private Map<GameColor, Integer> diseaseCubes;

	public CubeDataImpl() {
		this.diseaseCubes = new EnumMap<GameColor, Integer>(GameColor.class);
	}

	@Override
	public int getDiseaseCubeCount(GameColor color) {
		return diseaseCubes.getOrDefault(color, 0);
	}

	@Override
	public boolean hasDiseaseCube(GameColor color) {
		return getDiseaseCubeCount(color) > 0;
	}

	@Override
	public void addDiseaseCube(GameColor color, int count) {
		int i = diseaseCubes.getOrDefault(color, 0);
		i += count;
		diseaseCubes.put(color, i);
	}

	@Override
	public void setDiseaseCubeCount(GameColor color, int count) {
		if (count > 0)
			diseaseCubes.put(color, count);
		else
			diseaseCubes.remove(color);
	}

	@Override
	public void removeAllDiseaseCube(GameColor color) {
		removeDiseaseCube(color, diseaseCubes.getOrDefault(color, 0));
	}

	@Override
	public void removeDiseaseCube(GameColor color, int count) {
		int i = diseaseCubes.getOrDefault(color, 0);
		i -= count;
		if (i < 0)
			i = 0;
		if (i > 0) {
			diseaseCubes.put(color, i);
		} else {
			diseaseCubes.remove(color);
		}
	}

	@Override
	public Set<GameColor> getExistingDiseases() {
		EnumSet<GameColor> set = EnumSet.noneOf(GameColor.class);
		diseaseCubes.forEach((color, count) -> {
			if (count > 0)
				set.add(color);
		});
		return set;
	}
}
