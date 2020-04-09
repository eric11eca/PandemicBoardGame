package testPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import game.player.Player;
import game.player.PlayerImpl;

public class TestConsumeAction {

	@Test
	public void testConsumeAction() {
		medicData.action = 4;
		Player medic = new PlayerImpl(new Board(), medicData);
		medic.consumeAction();
		assertEquals(3, medicData.action);
	}
}
