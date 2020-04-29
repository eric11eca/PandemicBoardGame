package test.game.disease;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.GameColor;
import game.disease.CubeData;
import game.disease.CubeDataImpl;

public class TestCubeDataImpl {
	CubeData cityDisease;

	@Before
	public void setup() {
		cityDisease = new CubeDataImpl();
		cityDisease.setDiseaseCubeCount(GameColor.YELLOW, 1);
		cityDisease.setDiseaseCubeCount(GameColor.RED, 1);
		cityDisease.setDiseaseCubeCount(GameColor.BLUE, 1);
		cityDisease.setDiseaseCubeCount(GameColor.BLACK, 1);
	}

	@Test
	public void testExistingDisease() {
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();
		assertTrue(diseasesColors.contains(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.BLUE));
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertTrue(diseasesColors.contains(GameColor.BLACK));

		cityDisease.removeDiseaseCube(GameColor.RED);
		diseasesColors = cityDisease.getExistingDiseases();
		assertFalse(diseasesColors.contains(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.BLUE));
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertTrue(diseasesColors.contains(GameColor.BLACK));
	}

	@Test
	public void testSetCountToZero() {
		cityDisease.setDiseaseCubeCount(GameColor.RED, 0);
		cityDisease.setDiseaseCubeCount(GameColor.BLACK, 0);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();
		assertFalse(diseasesColors.contains(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.BLUE));
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertFalse(diseasesColors.contains(GameColor.BLACK));
	}

	@Test
	public void testAddDiseaseCube() {
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.YELLOW);
		cityDisease.addDiseaseCube(GameColor.RED);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();

		assertTrue(diseasesColors.contains(GameColor.RED));
		assertEquals(2, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(3, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));
	}

	@Test
	public void testAddDiseaseCubeImp() {
		cityDisease.addDiseaseCube(GameColor.RED, 4);
		assertEquals(5, cityDisease.getDiseaseCubeCount(GameColor.RED));
	}

	@Test
	public void testRemoveDiseaseCube() {
		cityDisease.removeDiseaseCube(GameColor.RED);
		Set<GameColor> diseasesColors = cityDisease.getExistingDiseases();

		assertFalse(diseasesColors.contains(GameColor.RED));
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.RED));
		assertTrue(diseasesColors.contains(GameColor.YELLOW));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.YELLOW));
	}

	@Test
	public void testRemoveDiseaseCubeWithCount() {
		cityDisease.setDiseaseCubeCount(GameColor.RED, 5);
		cityDisease.removeDiseaseCube(GameColor.RED, 2);
		assertEquals(3, cityDisease.getDiseaseCubeCount(GameColor.RED));
	}

	@Test
	public void testRemoveDiseaseCubeOverCount() {
		cityDisease.setDiseaseCubeCount(GameColor.RED, 5);
		cityDisease.removeDiseaseCube(GameColor.RED, 6);
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.RED));
	}

	@Test
	public void testRemoveAllDiseaseCubes() {
		assertTrue(cityDisease.hasDiseaseCube(GameColor.BLACK));
		assertEquals(1, cityDisease.getDiseaseCubeCount(GameColor.BLACK));

		cityDisease.removeAllDiseaseCube(GameColor.BLACK);
		assertEquals(0, cityDisease.getDiseaseCubeCount(GameColor.BLACK));
		assertFalse(cityDisease.hasDiseaseCube(GameColor.BLACK));
	}

}
