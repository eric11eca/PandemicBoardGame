package testPlayerCardAction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cardActions.EventCardAction;
import cards.ForecastEvent;
import data.Board;

public class TestEventCardAction {
	EventCardAction eventCardAction;
	Board board;
	@Before
	public void setup() {
		Board.setNull();
		board = Board.getInstance();
		eventCardAction = new EventCardAction(board);
	}
	
	@Test
	public void testEventCardActionSuccess() {
		board.eventCards = EasyMock.createMock(HashMap.class);
		String forecast = "Forecast";
		EasyMock.expect(board.eventCards.containsKey(forecast)).andReturn(true);
		ForecastEvent forecastEvent = EasyMock.createMock(ForecastEvent.class);
		EasyMock.expect(board.eventCards.get(forecast)).andReturn(forecastEvent);
		forecastEvent.executeEvent();
		EasyMock.replay(board.eventCards, forecastEvent);
		assertTrue(eventCardAction.executeEventCard(forecast));
		EasyMock.verify(board.eventCards, forecastEvent);
	}
	
	
	@Test
	public void testFailToExecuteEventCardAction() {
		assertFalse(eventCardAction.executeEventCard("Fake Event"));
	}
}
