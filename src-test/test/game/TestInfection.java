package test.game;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import game.GameColor;
import game.GameState;
import game.Infection;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;

public class TestInfection {

	@Test
	public void testGameLose() {
		GameState game = EasyMock.mock(GameState.class);
		Deck empty = EasyMock.mock(Deck.class);
		EasyMock.expect(empty.isEmpty()).andReturn(true);
		Infection infection = new Infection(empty, null, null, game, null);
		game.triggerLose();
		EasyMock.expectLastCall().andVoid();
		EasyMock.replay(game, empty);

		assertFalse(infection.infectOnce().isPresent());
		EasyMock.verify(game, empty);
	}

	@Test
	public void testSuccessInfect() {

		City city = EasyMock.mock(City.class);
		city.infect(null, null);
		EasyMock.expectLastCall().andVoid();
		EasyMock.expect(city.getColor()).andReturn(GameColor.BLUE);
		Deck infect = EasyMock.mock(Deck.class);
		EasyMock.expect(infect.isEmpty()).andReturn(false);
		EasyMock.expect(infect.takeTopCard()).andReturn(new CardCity(city));
		Deck discard = EasyMock.mock(Deck.class);
		discard.putOnTop(new CardCity(city));
		EasyMock.expectLastCall().andVoid();
		GameCubePool disease = EasyMock.mock(GameCubePool.class);
		EasyMock.expect(disease.isDiseaseEradicated(GameColor.BLUE)).andReturn(false);

		Infection infection = new Infection(infect, discard, null, null, disease);
		EasyMock.replay(city, infect, discard, disease);
		assertTrue(infection.infectOnce().isPresent());
		EasyMock.verify(city, infect, discard, disease);
	}

	@Test
	public void testSkipEradicatedInfection() {
		City city = EasyMock.mock(City.class);
		EasyMock.expect(city.getColor()).andReturn(GameColor.BLUE);
		Deck infect = EasyMock.mock(Deck.class);
		EasyMock.expect(infect.isEmpty()).andReturn(false);
		EasyMock.expect(infect.takeTopCard()).andReturn(new CardCity(city));
		Deck discard = EasyMock.mock(Deck.class);
		discard.putOnTop(new CardCity(city));
		EasyMock.expectLastCall().andVoid();
		GameCubePool disease = EasyMock.mock(GameCubePool.class);
		EasyMock.expect(disease.isDiseaseEradicated(GameColor.BLUE)).andReturn(true);

		Infection infection = new Infection(infect, discard, null, null, disease);
		EasyMock.replay(city, infect, discard, disease);
		assertTrue(infection.infectOnce().isPresent());
		EasyMock.verify(city, infect, discard, disease);

	}
}
