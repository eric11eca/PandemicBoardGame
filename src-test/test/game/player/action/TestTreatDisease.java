package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;

import game.GameColor;
import game.cards.Deck;
import game.city.City;
import game.disease.CubeData;
import game.disease.CubeDataImpl;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.action.Action;
import game.player.action.ActionTreatDisease;
import mock.MockCityBuilder;
import mock.MockInteraction;

public class TestTreatDisease {
	Player player;
	City newyorkCity;
	boolean cbExecuted;
	MockInteraction interaction;

	Deck discard;
	CubeData diseases;

	@Before
	public void setup() {
		diseases = new CubeDataImpl();
		diseases.addDiseaseCube(GameColor.BLUE);
		newyorkCity = new MockCityBuilder().name("NewYork").color(GameColor.BLUE).cubeData(diseases).build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectColorFrom(this::selectColorFrom);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);
	}

	@Test
	public void testSuccessTreatDisease() {
		assertEquals(newyorkCity, player.getLocation());
		assertEquals(1, newyorkCity.getExistingDiseases().size());

		Action action = new ActionTreatDisease(player, interaction, Collections.emptySet());
		assertTrue(action.canPerform());
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(newyorkCity.getExistingDiseases().size(), 0);
	}

	@Test
	public void testEradicateDisease() {
		newyorkCity.initializeDisease(3);

		Set<GameColor> curedDisease = EnumSet.of(GameColor.BLUE);

		assertEquals(newyorkCity, player.getLocation());
		assertEquals(newyorkCity.getDiseaseCubeCount(GameColor.BLUE), 3);

		Action action = new ActionTreatDisease(player, interaction, curedDisease);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(newyorkCity.getExistingDiseases().size(), 0);
	}

	@Test
	public void testCannotPerform() {
		newyorkCity.initializeDisease(0);
		Action action = new ActionTreatDisease(player, interaction, Collections.emptySet());
		assertFalse(action.canPerform());
	}

	private void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		assertTrue(colors.contains(GameColor.BLUE));
		callback.accept(GameColor.BLUE);
	}
}
