package test.game.disease;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EnumSet;

import org.easymock.EasyMock;
import org.junit.Test;

import game.GameColor;
import game.GameState;
import game.disease.CureSet;

public class TestCureSet {

	@Test
	public void testAdd() {
		GameState game = EasyMock.mock(GameState.class);
		CureSet set = new CureSet(game, EnumSet.of(GameColor.BLUE, GameColor.RED, GameColor.YELLOW));
		game.triggerWin();
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(game);
		assertFalse(set.add(GameColor.BLUE));
		assertTrue(set.add(GameColor.BLACK));
		EasyMock.verify(game);
	}
}
