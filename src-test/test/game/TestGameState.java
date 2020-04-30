package test.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import data.GameProperty;
import game.GameState;

public class TestGameState {
	GameState game;
	
	@Test
	public void testIncreaseOutbreakLevel() {
		game = new GameState();
		int prevOutbreakCount = game.getOutbreakLevel();
		game.increaseOutbreakLevel(1);
		assertEquals(prevOutbreakCount+1, game.getOutbreakLevel());
	}
	
	@Test
	public void testIncreaseOutbreakLevelLose() {
		game = new GameState();
		for (int i = 0; i < 7; i++) {
			game.increaseOutbreakLevel(1);
		}
		assertFalse(game.isLost());
		game.increaseOutbreakLevel(1);
		assertTrue(game.isLost());
	}

	@Test
	public void testIncreaseInfectionRate() {
		game = new GameState();
		int[] infectionRates = GameProperty.getInstance().getIntArray("INFECTION_RATES");
		int prevInfectionIndex = game.getInfectionRateIndex();
		game.increaseInfectionRate();
		assertEquals(prevInfectionIndex+1, game.getInfectionRateIndex());
		assertEquals(infectionRates[1], game.getInfectionRate());
	}
}
