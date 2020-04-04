package testPlayerCardAction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import data.Board;
import game.event.EventForecast;
import game.player.action.ActionEventCard;

public class TestEventCardAction {
	ActionEventCard eventCardAction;
	Board board;
	@Before
	public void setup() {
		board = new Board();
		eventCardAction = new ActionEventCard(board);
	}
	
	@Test
	public void testEventCardActionSuccess() {
		board.eventCards = EasyMock.createMock(HashMap.class);
		String forecast = "Forecast";
		EasyMock.expect(board.eventCards.containsKey(forecast)).andReturn(true);
		EventForecast forecastEvent = EasyMock.createMock(EventForecast.class);
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
