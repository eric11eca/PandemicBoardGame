package test.game.player.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
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
	MockCityBuilder newyorkBuilder;

	MockCityBuilder cityFactory = new MockCityBuilder();
	boolean cbExecuted;
	MockInteraction interaction;

	Deck discard;
	CubeData diseases;

	@Before
	public void setup() {
		newyorkBuilder = new MockCityBuilder().name("NewYork");
		diseases = new CubeDataImpl();
		diseases.addDiseaseCube(GameColor.BLUE);
		newyorkBuilder = newyorkBuilder.cubeData(diseases);
		newyorkCity = newyorkBuilder.build();

		cbExecuted = false;
		interaction = new MockInteraction();
		interaction.implementSelectColorFrom(this::selectColorFrom);

		discard = new Deck();
		player = new PlayerImpl(null, newyorkCity, discard, interaction);
	}

	@Test
	public void testSuccessTreatDisease() {
		Set<GameColor> curedDisease = new HashSet<>();
		curedDisease.add(GameColor.RED);

		assertEquals(newyorkCity, player.getLocation());
		assertEquals(newyorkCity.getExistingDiseases().size(), 1);
		
		Action action = new ActionTreatDisease(player, interaction, curedDisease);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(newyorkCity.getExistingDiseases().size(), 0);
	}
	
	@Test
	public void testEradicateDisease() {
		newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkBuilder = newyorkBuilder.color(GameColor.BLUE);
		newyorkCity = newyorkBuilder.build();
		newyorkCity.initializeDisease(3);
		
		player = new PlayerImpl(null, newyorkCity, discard, interaction);
		
		Set<GameColor> curedDisease = new HashSet<>();
		curedDisease.add(GameColor.BLUE);
		
		assertEquals(newyorkCity, player.getLocation());
		assertEquals(newyorkCity.getDiseaseCubeCount(GameColor.BLUE), 3);
		
		Action action = new ActionTreatDisease(player, interaction, curedDisease);
		action.perform(() -> cbExecuted = true);

		assertTrue(cbExecuted);
		assertEquals(newyorkCity.getExistingDiseases().size(), 0);
	}

	private void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) {
		assertTrue(colors.contains(GameColor.BLUE));
		callback.accept(GameColor.BLUE);
	}
}
