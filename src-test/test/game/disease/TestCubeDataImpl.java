package test.game.disease;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import game.GameColor;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class TestCubeDataImpl {

	@Test
	public void test() {
		CubeData cityDisease = new CubeDataImpl();
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.RED);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));

		cityDisease.removeDiseaseCube(GameColor.RED);
		diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertFalse(diseasesColors.contains(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));

		cityDisease.addDiseaseCube(GameColor.BLUE);
		cityDisease.addDiseaseCube(GameColor.BLACK, 3);
		diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertFalse(diseasesColors.contains(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.BLUE));
		assertTrue(diseasesColors.contains(GameColor.BLACK));
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.BLUE));
		assertEquals(3, cityDisease.getDiseaseCubeCount(GameColor.BLACK));

		cityDisease.removeAllDiseaseCube(GameColor.BLACK);
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.BLACK));
	}

}
