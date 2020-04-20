package test.game.player;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.player.PlayerRole;
import mock.MockGameProperty;

public class TestPlayerRole {
	MockGameProperty property;

	@Before
	public void setupProperty() {
		property = new MockGameProperty();
		for (PlayerRole role : PlayerRole.values()) {
			property.put(role.name() + "_NAME", "Name of " + role.name());
		}
		property.inject();
	}

	@After
	public void cleanupProperty() {
		property.resetAndEject();
	}

	@Test
	public void testRoleName() {
		for (PlayerRole role : PlayerRole.values()) {
			assertEquals("Name of " + role.name(), role.getRoleName());
		}
	}
}
