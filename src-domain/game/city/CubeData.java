package game.city;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import data.GameColor;

public class CubeData {
	private Map<GameColor, Integer> diseaseCubes;

	public CubeData() {
		this.diseaseCubes = new EnumMap<GameColor, Integer>(GameColor.class);
	}

	public int getDiseaseCubeCount(GameColor color) {
		return diseaseCubes.getOrDefault(color, 0);
	}

	public boolean hasDiseaseCube(GameColor color) {
		return getDiseaseCubeCount(color) > 0;
	}

	public void addDiseaseCube(GameColor color) {
		addDiseaseCube(color, 1);
	}

	public void addDiseaseCube(GameColor color, int count) {
		int i = diseaseCubes.getOrDefault(color, 0);
		i += count;
		diseaseCubes.put(color, i);
	}

	public void setDiseaseCubeCount(GameColor color, int count) {
		if (count > 0)
			diseaseCubes.put(color, count);
		else
			diseaseCubes.remove(color);
	}

	public void removeDiseaseCube(GameColor color) {
		removeDiseaseCube(color, 1);
	}

	public void removeAllDiseaseCube(GameColor color) {
		removeDiseaseCube(color, diseaseCubes.getOrDefault(color, 0));
	}

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

	public Set<GameColor> getExistingDiseases() {
		EnumSet<GameColor> set = EnumSet.noneOf(GameColor.class);
		diseaseCubes.forEach((color, count) -> {
			if (count > 0)
				set.add(color);
		});
		return set;
	}
}
