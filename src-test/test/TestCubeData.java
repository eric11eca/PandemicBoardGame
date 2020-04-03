package test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import data.GameColor;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class TestCubeData {

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
	}

}
