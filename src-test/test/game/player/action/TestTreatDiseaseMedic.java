package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.GameColor;
import game.city.City;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionTreatDiseaseMedic;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestTreatDiseaseMedic {
	private Player player;
	private City newyorkCity;
	private boolean cbExecuted;
	private MockInteraction interaction;

	@Before
	public void setup() {
		newyorkCity = new MockCityBuilder().name("NewYork").color(GameColor.BLUE).build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectColorFrom(this::selectColorFrom);

		player = new PlayerImpl(null, newyorkCity, null, interaction);
	}

	@Test
	public void testSuccessTreatDisease() {
		assertEquals(newyorkCity, player.getLocation());
		newyorkCity.initializeDisease(3);

		Action action = new ActionTreatDiseaseMedic(player, interaction);
		assertFalse(action.isOncePerTurn());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		interaction.verifySelectColorCalled(1);

		assertTrue(cbExecuted);
		assertEquals(newyorkCity.getExistingDiseases().size(), 0);
	}

	@Test
	public void testCannotPerform() {
		newyorkCity.initializeDisease(0);
		Action action = new ActionTreatDiseaseMedic(player, interaction);
		assertFalse(action.canPerform());
	}

	private void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		assertTrue(colors.contains(GameColor.BLUE));
		callback.accept(GameColor.BLUE);
	}
}
