package TestPlayerCardAction;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import Card.AirliftEvent;
import Card.EventCardAction;
import Card.ForecastEvent;
import Card.GovernmentGrantEvent;
import Card.OneQueitNightEvent;
import Card.PlayerCard;
import Card.ResilientPopulationEvent;
import Initialize.Board;

public class TestEventCardAction {
	Board board;
	EventCardAction eventCardAction;
	
	@Before 
	public void setup() {
		board = new Board();
	}
	
	@Test
	public void testAirlift() {
		AirliftEvent airlift = EasyMock.partialMockBuilder(AirliftEvent.class)
								.addMockedMethod("airlift")
								.withConstructor(board)
								.createMock();
		airlift.airlift();
		
		EasyMock.replay(airlift);
		String cardName = "Airlift";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		EventCardAction eventCardAction = new EventCardAction(board, card);
		eventCardAction.airliftEvent = airlift;
		
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertTrue(eventCardPlayed);
		EasyMock.verify(airlift);;
	}

	@Test
	public void testForecast() {
		ForecastEvent forecast = EasyMock.partialMockBuilder(ForecastEvent.class)
								.addMockedMethod("forecast")
								.withConstructor(board)
								.createMock();
		forecast.forecast();
		
		EasyMock.replay(forecast);
		String cardName = "Forecast";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		eventCardAction = new EventCardAction(board, card);
		eventCardAction.forecastEvent = forecast;
		
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertTrue(eventCardPlayed);
		EasyMock.verify(forecast);
	}

	@Test
	public void testOneQuietNight() {
		OneQueitNightEvent oneNight = EasyMock.partialMockBuilder(OneQueitNightEvent.class)
				.addMockedMethod("skipNextInfection")
				.withConstructor(board)
				.createMock();
		oneNight.skipNextInfection();

		EasyMock.replay(oneNight);
		String cardName = "OneQuietNight";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		eventCardAction = new EventCardAction(board, card);
		eventCardAction.nightEvent = oneNight;
		
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertTrue(eventCardPlayed);
		EasyMock.verify(oneNight);
	}
	
	@Test
	public void testGovernmentGrant() {
		GovernmentGrantEvent grant = EasyMock.partialMockBuilder(GovernmentGrantEvent.class)
				.addMockedMethod("addResearchStation")
				.withConstructor(board)
				.createMock();
		grant.addResearchStation();

		EasyMock.replay(grant);
		String cardName = "GovernmentGrant";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		eventCardAction = new EventCardAction(board, card);
		eventCardAction.grantEvent = grant;
		
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertTrue(eventCardPlayed);
		EasyMock.verify(grant);
	}
	
	@Test
	public void testResilientPopulation() {
		ResilientPopulationEvent resilient = EasyMock.partialMockBuilder(ResilientPopulationEvent.class)
				.addMockedMethod("resilientPopulation")
				.withConstructor(board)
				.createMock();
		resilient.resilientPopulation();

		EasyMock.replay(resilient);
		String cardName = "ResilientPopulation";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		eventCardAction = new EventCardAction(board, card);
		eventCardAction.resilientEvent = resilient;
		
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertTrue(eventCardPlayed);
		EasyMock.verify(resilient);
	}
	
	@Test
	public void testNoEvent() {
		String cardName = " ";
		PlayerCard card = new PlayerCard(Board.CardType.EVENTCARD, cardName);
		eventCardAction = new EventCardAction(board, card);
		boolean eventCardPlayed = eventCardAction.excuteEventCard();
		assertFalse(eventCardPlayed);
	}

}
