package test.game.disease;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import game.GameColor;
import game.disease.CityCubeData;
import game.disease.CubeData;

public class TestCityCubeData {

	@Test
	public void testAddDiseaseCube() {
		CubeData gamePool = EasyMock.mock(CubeData.class);
		gamePool.removeDiseaseCube(GameColor.RED, 2);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(gamePool);

		CityCubeData data = new CityCubeData(gamePool);
		data.addDiseaseCube(GameColor.RED, 2);
		assertEquals(2, data.getDiseaseCubeCount(GameColor.RED));
		assertTrue(data.hasDiseaseCube(GameColor.RED));
		EasyMock.verify(gamePool);
	}

	@Test
	public void testSetCount() {
		CubeData gamePool = EasyMock.mock(CubeData.class);
		gamePool.removeDiseaseCube(GameColor.RED, 5);
		EasyMock.expectLastCall().andVoid();
		gamePool.addDiseaseCube(GameColor.RED, 0);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(gamePool);

		CityCubeData data = new CityCubeData(gamePool);
		data.setDiseaseCubeCount(GameColor.RED, 5);
		assertEquals(5, data.getDiseaseCubeCount(GameColor.RED));
		assertTrue(data.hasDiseaseCube(GameColor.RED));
		EasyMock.verify(gamePool);
	}

	@Test
	public void testRemoveDiseaseCube() {
		CubeData gamePool = EasyMock.mock(CubeData.class);
		gamePool.removeDiseaseCube(GameColor.RED, 5);
		EasyMock.expectLastCall().andVoid();
		gamePool.addDiseaseCube(GameColor.RED, 2);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(gamePool);

		CityCubeData data = new CityCubeData(gamePool);
		data.addDiseaseCube(GameColor.RED, 5);
		assertEquals(5, data.getDiseaseCubeCount(GameColor.RED));
		data.removeDiseaseCube(GameColor.RED, 2);
		assertTrue(data.hasDiseaseCube(GameColor.RED));
		EasyMock.verify(gamePool);
	}

	@Test
	public void testRemoveAllDiseaseCube() {
		CubeData gamePool = EasyMock.mock(CubeData.class);
		gamePool.removeDiseaseCube(GameColor.RED, 5);
		EasyMock.expectLastCall().andVoid();
		gamePool.addDiseaseCube(GameColor.RED, 5);
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(gamePool);

		CityCubeData data = new CityCubeData(gamePool);
		data.addDiseaseCube(GameColor.RED, 5);
		assertEquals(5, data.getDiseaseCubeCount(GameColor.RED));
		data.removeAllDiseaseCube(GameColor.RED);
		assertFalse(data.hasDiseaseCube(GameColor.RED));
		EasyMock.verify(gamePool);
	}

	@Test
	public void testExistingDiseases() {
		CityCubeData data = new CityCubeData(EasyMock.mock(CubeData.class));
		assertTrue(data.getExistingDiseases().isEmpty());
		data.addDiseaseCube(GameColor.BLACK);
		assertTrue(data.getExistingDiseases().contains(GameColor.BLACK));
		assertFalse(data.getExistingDiseases().contains(GameColor.YELLOW));
		data.addDiseaseCube(GameColor.YELLOW);
		assertTrue(data.getExistingDiseases().contains(GameColor.BLACK));
		assertTrue(data.getExistingDiseases().contains(GameColor.YELLOW));
		data.removeDiseaseCube(GameColor.BLACK);
		assertFalse(data.getExistingDiseases().contains(GameColor.BLACK));
		assertTrue(data.getExistingDiseases().contains(GameColor.YELLOW));
		data.removeDiseaseCube(GameColor.YELLOW);
		assertTrue(data.getExistingDiseases().isEmpty());
	}

}
