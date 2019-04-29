package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Card.ForecastEvent;
import Initialize.Board;
import Initialize.City;
import Player.Player;
import javafx.util.Pair;

public class TestForecastEvent {
	Board board;
	ForecastEvent forecast;
	Player player;
	
	@Before 
	public void setup() {
		board = new Board();
		forecast = new ForecastEvent(board);
		setupBoard();
	}
	
	public void setupBoard() {
		board.validInfectionCard.add("NewYork");
		board.validInfectionCard.add("TaiPei");
		board.validInfectionCard.add("Chicago");
		board.validInfectionCard.add("Moscow");
		board.validInfectionCard.add("Cairo");
		board.validInfectionCard.add("Seattle");
		board.validInfectionCard.add("Madrid");
		board.validInfectionCard.add("Paris");
		
		City city1 = new City();
		City city2 = new City();
		City city3 = new City();
		City city4 = new City();
		City city5 = new City();
		City city6 = new City();
		City city7 = new City();
		City city8 = new City();
		
		city1.cityName = "Cairo";
		city2.cityName = "Moscow";
		city3.cityName = "NewYork";
		city4.cityName = "TaiPei";
		city5.cityName = "Seattle";
		city6.cityName = "Chicago";
		city7.cityName = "Madrid";
		city8.cityName = "Paris";
		
		city1.color = "Yellow";
		city2.color = "Yellow";
		city3.color = "Red";
		city4.color = "Blue";
		city5.color = "Black";
		city6.color = "Blue";
		city7.color = "Red";
		city8.color = "Yellow";
		
		board.cities.put("Cairo", city1);
		board.cities.put("Moscow", city1);
		board.cities.put("NewYork", city1);
		board.cities.put("TaiPei", city1);
		board.cities.put("Seattle", city1);
		board.cities.put("Chicago", city1);
		board.cities.put("Madrid", city1);
		board.cities.put("Paris", city1);
		
		board.rearrangeInstruction.add(new Pair<String, Integer>("Cairo", 0));
		board.rearrangeInstruction.add(new Pair<String, Integer>("Moscow", 1));
		board.rearrangeInstruction.add(new Pair<String, Integer>("NewYork", 2));
		board.rearrangeInstruction.add(new Pair<String, Integer>("TaiPei", 3));
		board.rearrangeInstruction.add(new Pair<String, Integer>("Seattle", 4));
		board.rearrangeInstruction.add(new Pair<String, Integer>("Chicago", 5));
		board.rearrangeInstruction.add(new Pair<String, Integer>("Madrid", 6));
		board.rearrangeInstruction.add(new Pair<String, Integer>("Paris", 7));
	}
	
	@Test 
	public void testForecast() {
		forecast.forecast();
		assertEquals(6, board.infectionCardForecast.size());
		assertEquals("Cairo", board.validInfectionCard.get(0));
		assertEquals("TaiPei", board.validInfectionCard.get(3));
		assertEquals("Paris", board.validInfectionCard.get(7));
	}
	
	@Test
	public void testReviewCardPhase() {
		forecast.reviewCard();
		assertEquals(6, board.infectionCardForecast.size());
	}
	
	@Test
	public void testArrangeCardPhase() {
		forecast.arrangeCard();
		assertEquals("Cairo", board.validInfectionCard.get(0));
		assertEquals("TaiPei", board.validInfectionCard.get(3));
		assertEquals("Paris", board.validInfectionCard.get(7));
	}
}
