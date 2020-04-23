package test.game.disease;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;

import data.GameProperty;
import game.GameColor;
import game.GameState;
import game.Infection;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;
import game.player.Player;
import game.player.PlayerImpl;
import game.player.special.QuarantineChecker;
import test.MockCityBuilder;
import test.MockInteraction;

public class testInfection {
	Infection infection;
	GameState game;
	Deck infectionDeck;
	Deck infectionDiscard;
	GameCubePool gameCubePool;
	Predicate<City> quarantineChecker;
	
	Player player;
	MockInteraction interaction;
	City newyorkCity, chicagoCity;
	Card newyorkCard;
	MockCityBuilder cityFactory = new MockCityBuilder();
	
	@Before
	public void setup() {
		game = new GameState();
		infectionDeck = new Deck();
		infectionDiscard = new Deck();
		gameCubePool = new GameCubePool(game);
		
		MockCityBuilder newyorkBuilder = new MockCityBuilder().name("NewYork");
		newyorkBuilder = newyorkBuilder.color(GameColor.BLUE);
		newyorkCity = newyorkBuilder.build();
		newyorkCard = new CardCity(newyorkCity);
		
		MockCityBuilder chicagoBuilder = new MockCityBuilder().name("Chicago");
		chicagoCity = chicagoBuilder.build();
		
		interaction = new MockInteraction();
		player = new PlayerImpl(0, chicagoCity, new Deck(), interaction);
		
		quarantineChecker = new QuarantineChecker(player);
		infection = new Infection(infectionDeck, infectionDeck, quarantineChecker, game, gameCubePool);
	}
	
	@Test
	public void testGameLose() {
		assertFalse(game.isLost());
		assertTrue(infectionDeck.isEmpty());
		
		infection.infectOnce();
		assertTrue(game.isLost());
	}
	
	@Test
	public void testSuccesInfect() {
		infectionDeck.putOnTop(newyorkCard);
		assertFalse(infectionDeck.isEmpty());
		assertFalse(gameCubePool.isDiseaseEradicated(GameColor.BLUE));
		assertFalse(quarantineChecker.test(newyorkCity));
		assertFalse(newyorkCity.getExistingDiseases().contains(GameColor.BLUE));
		
		infection.infectOnce();
		assertTrue(newyorkCity.getExistingDiseases().contains(GameColor.BLUE));
	}
	
	@Test
	public void testSkipEradicatedInfection() {
		infectionDeck.putOnTop(newyorkCard);
		int maxCount = GameProperty.getInstance().getInt("MAX_DISEASE_CUBE_PER_COLOR");
		gameCubePool.setDiseaseCubeCount(GameColor.BLUE, maxCount);
		
		assertTrue(gameCubePool.isDiseaseEradicated(GameColor.BLUE));
		assertFalse(quarantineChecker.test(newyorkCity));
		assertFalse(newyorkCity.getExistingDiseases().contains(GameColor.BLUE));
		
		infection.infectOnce();
		assertFalse(newyorkCity.getExistingDiseases().contains(GameColor.BLUE));
	}
}
