package test.game.disease;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import game.GameColor;
import game.GameState;
import game.disease.GameCubePool;

public class TestCubePool {
	GameState state = new GameState();
	GameCubePool cubePool = new GameCubePool(state);
	
	@Test
	public void testSetCubeCount() {
		int cubeCount = 23;
		cubePool.setDiseaseCubeCount(GameColor.BLUE, cubeCount);
		assertEquals(23, cubePool.getDiseaseCubeCount(GameColor.BLUE));
	}
	
	@Test
	public void testAddCubeCount() {
		int cubeCount = 10;
		cubePool.setDiseaseCubeCount(GameColor.BLUE, cubeCount);
		cubePool.addDiseaseCube(GameColor.BLUE, cubeCount);
		assertEquals(20, cubePool.getDiseaseCubeCount(GameColor.BLUE));
	}
	
	@Test
	public void testAddCubeCountAtBoundry() {
		int cubeCount = 14;
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 10);
		cubePool.addDiseaseCube(GameColor.BLUE, cubeCount);
		assertEquals(24, cubePool.getDiseaseCubeCount(GameColor.BLUE));
	}
	
	@Test
	public void testExceedMaxCubeCountWhenSet() {
		int cubeCount = 25;
		Exception exception = assertThrows(RuntimeException.class, () -> {
			cubePool.setDiseaseCubeCount(GameColor.BLUE, cubeCount);
		});
		
		String expectedMessage = "Disease Cube Overflow: ";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testExceedMaxCubeCountWhenAdd() {
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 10);
		int cubeCount = 15;
		Exception exception = assertThrows(RuntimeException.class, () -> {
			cubePool.addDiseaseCube(GameColor.BLUE, cubeCount);
		});
		
		String expectedMessage = "Disease Cube Overflow: ";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void testHasDiseaseCube() {
		int cubeCount = 10;
		cubePool.setDiseaseCubeCount(GameColor.BLUE, cubeCount);
		cubePool.setDiseaseCubeCount(GameColor.RED, cubeCount);
		
		assertTrue(cubePool.hasDiseaseCube(GameColor.BLUE));
		assertTrue(cubePool.hasDiseaseCube(GameColor.RED));
		assertFalse(cubePool.hasDiseaseCube(GameColor.BLACK));
		assertFalse(cubePool.hasDiseaseCube(GameColor.YELLOW));
	}
	
	@Test 
	public void testInitDiseaseCube() {
		int maxCubeCount = 24;
		cubePool.initialize();
		assertEquals(maxCubeCount, cubePool.getDiseaseCubeCount(GameColor.BLUE));
		assertEquals(maxCubeCount, cubePool.getDiseaseCubeCount(GameColor.RED));
		assertEquals(maxCubeCount, cubePool.getDiseaseCubeCount(GameColor.BLACK));
		assertEquals(maxCubeCount, cubePool.getDiseaseCubeCount(GameColor.YELLOW));
	}
	
	@Test
	public void testRemoveDiseaseCube() {
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 10);
		cubePool.removeDiseaseCube(GameColor.BLUE, 5);
		assertEquals(5, cubePool.getDiseaseCubeCount(GameColor.BLUE));
		assertFalse(state.isLost());
		assertFalse(state.isWon());
	}
	
	@Test
	public void testRemoveDiseaseCubeAtBoundry() {
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 5);
		cubePool.removeDiseaseCube(GameColor.BLUE, 5);
		assertEquals(0, cubePool.getDiseaseCubeCount(GameColor.BLUE));
		assertFalse(state.isLost());
		assertFalse(state.isWon());
	}
	
	@Test
	public void testRemoveDiseaseCubeLoss() {
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 5);
		cubePool.removeDiseaseCube(GameColor.BLUE, 10);
		assertEquals(5, cubePool.getDiseaseCubeCount(GameColor.BLUE));
		assertTrue(state.isLost());
	}
	
	@Test
	public void testRemoveAllDiseasesCube() {
		cubePool.setDiseaseCubeCount(GameColor.BLUE, 10);
		cubePool.removeAllDiseaseCube(GameColor.BLUE);
		assertEquals(0, cubePool.getDiseaseCubeCount(GameColor.BLUE));
		assertFalse(state.isLost());
		assertFalse(state.isWon());
	}
	
	@Test
	public void testRemoveDiseaseWin() {
		cubePool.initialize();
		cubePool.removeDiseaseCube(GameColor.BLUE, 0);
		assertTrue(state.isWon());
	}
	
	@Test
	public void testGetExistingDiseases() {
		cubePool.initialize();
		Set<GameColor> diseases = cubePool.getExistingDiseases();
		assertTrue(diseases.contains(GameColor.BLUE));
	}
}
