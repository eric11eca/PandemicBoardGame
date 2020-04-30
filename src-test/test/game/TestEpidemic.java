package test.game;

import java.util.Optional;
import java.util.function.Predicate;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.Epidemic;
import game.GameColor;
import game.GameState;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;
import game.player.special.QuarantineChecker;

public class TestEpidemic {
	Epidemic epidemic;
	Deck infectionCards;
	Deck infectionDiscard;
	GameState game;
	Predicate<City> quarantineChecker; 
	GameCubePool gameCubePool;
	
	City newyork, chicago; 
	Card newyork_infect, chicago_infect;
	
	@Before
	public void setup() {
		infectionCards =  EasyMock.createNiceMock(Deck.class);
		infectionDiscard = EasyMock.createNiceMock(Deck.class);
		
		newyork = EasyMock.mock(City.class);
		chicago = EasyMock.mock(City.class);
		
		newyork_infect = EasyMock.mock(CardCity.class);
		chicago_infect = EasyMock.mock(CardCity.class);
		
		game = EasyMock.mock(GameState.class);
		quarantineChecker = EasyMock.mock(QuarantineChecker.class);
		gameCubePool = EasyMock.mock(GameCubePool.class);
		
		epidemic = new Epidemic(infectionCards, infectionDiscard, game, quarantineChecker, gameCubePool);
	}

	@Test
	public void testTriggerEpidemicWithNoDiseaseEradicated() {
		Optional<City> cities = Optional.of(newyork);
		EasyMock.expect(infectionCards.takeBottomCard()).andReturn(newyork_infect);
		EasyMock.expect(newyork_infect.getCity()).andReturn(cities);
		EasyMock.expect(newyork.getColor()).andReturn(GameColor.BLUE);
		EasyMock.expect(gameCubePool.isDiseaseEradicated(GameColor.BLUE)).andReturn(false);
		
		game.increaseInfectionRate();
		EasyMock.expectLastCall().andVoid();
		
		newyork.epidemicInfect(game, quarantineChecker);
		EasyMock.expectLastCall().andVoid();
		
		infectionDiscard.putOnTop(new CardCity(newyork));
		EasyMock.expectLastCall().andVoid();
		
		infectionDiscard.shuffle();
		EasyMock.expectLastCall().andVoid();
		
		infectionDiscard.clear();
		EasyMock.expectLastCall().andVoid();
		
		EasyMock.replay(game,gameCubePool,newyork,newyork_infect,infectionCards,infectionDiscard);
		epidemic.triggerEpidemic();
		EasyMock.verify(game,gameCubePool,newyork,newyork_infect,infectionCards,infectionDiscard);
	}
}
