package test.game.disease;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.GameColor;
import game.GameState;
import game.disease.CureSet;

public class TestCureSet {
	GameState game;
	CureSet cureSet;
	
	@Before
	public void setup() {
		game = EasyMock.mock(GameState.class);
		cureSet  = new CureSet(game, EnumSet.of(GameColor.BLUE, GameColor.YELLOW));
	}

	@Test
	public void testAdd() {
		game.triggerWin();
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(game);
		assertFalse(cureSet.add(GameColor.BLUE));
		assertTrue(cureSet.add(GameColor.BLACK));
		assertTrue(cureSet.add(GameColor.RED));
		EasyMock.verify(game);
	}
	
	@Test
	public void testRemove() {
		assertTrue(cureSet.remove(GameColor.BLUE));
		assertFalse(cureSet.remove(GameColor.BLACK));
	}

	@Test
	public void testAddAll() {
		GameColor[] colors2 = new GameColor[] {GameColor.BLUE, GameColor.YELLOW};
		assertFalse(cureSet.addAll(Arrays.asList(colors2)));
		assertEquals(2, cureSet.size());
		GameColor[] colors1 = new GameColor[] {GameColor.BLACK, GameColor.RED};
		assertTrue(cureSet.addAll(Arrays.asList(colors1)));
		assertEquals(4, cureSet.size());
	}
	
	@Test
	public void testContainsAll() {
		GameColor[] colors = new GameColor[] {GameColor.BLUE, GameColor.YELLOW};
		assertTrue(cureSet.containsAll(Arrays.asList(colors)));
		cureSet.clear();
		assertFalse(cureSet.containsAll(Arrays.asList(colors)));
		assertTrue(cureSet.isEmpty());
		
		GameColor[] colorsAll = new GameColor[] {
				GameColor.BLUE, GameColor.YELLOW, GameColor.BLACK, GameColor.RED};
		cureSet.addAll(Arrays.asList(colorsAll));
		
		GameColor[] retained = new GameColor[] {GameColor.BLUE, GameColor.YELLOW};
		assertTrue(cureSet.retainAll(Arrays.asList(retained)));
		assertTrue(cureSet.containsAll(Arrays.asList(colors)));
		assertFalse(cureSet.retainAll(Arrays.asList(retained)));
		assertFalse(cureSet.isEmpty());
		
		assertTrue(cureSet.removeAll(Arrays.asList(colors)));
		assertFalse(cureSet.containsAll(Arrays.asList(colors)));
		assertFalse(cureSet.removeAll(Arrays.asList(colors)));
		assertTrue(cureSet.isEmpty());
	}
	
	@Test
	public void testReturnedObject() {
		GameColor[] colors = new GameColor[] {GameColor.BLUE, GameColor.YELLOW};
		List<GameColor> colorList = Arrays.asList(colors);
		
		Iterator<GameColor> iterator = cureSet.iterator();
		while(iterator.hasNext()) {
			assertTrue(colorList.contains(iterator.next()));
		}
		
		Object[] cureArray = cureSet.toArray();
		assertTrue(Arrays.asList(cureArray).contains(GameColor.BLUE));
		
		GameColor[] cureTArray = cureSet.toArray(new GameColor[4]);
		assertTrue(Arrays.asList(cureTArray).contains(GameColor.BLUE));
	}
}
