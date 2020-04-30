package test.game.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import game.cards.Card;
import game.cards.CardCity;
import game.city.City;
import game.player.Player;
import game.player.PlayerController;
import game.player.PlayerRole;
import game.player.action.Action;
import game.player.action.ActionType;

public class TestPlayerController {
	Player player;
	Map<ActionType, Action> actionMap;
	
	PlayerController controller;
	
	ActionType actionType;
	Action action;
	
	@Before
	public void setup() {
		player = EasyMock.createNiceMock(Player.class);
		actionMap = EasyMock.createNiceMock(Map.class);
		controller = new PlayerController(player, actionMap);
		
		actionType = ActionType.BUILD_STATION;
		action = EasyMock.createNiceMock(Action.class);
	}
	
	@Test
	public void testGetPlayerInfo() {
		City newyork = EasyMock.mock(City.class);
		EasyMock.expect(player.getRole()).andReturn(PlayerRole.SCIENTIST);
		EasyMock.expect(player.getLocation()).andReturn(newyork);
		
		EasyMock.replay(newyork, player);
		assertEquals(PlayerRole.SCIENTIST, controller.getRole());
		assertEquals(newyork, controller.getPlayerCity());
		EasyMock.verify(newyork, player);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testPlayerHand() {
		Card newyorkCard = EasyMock.mock(CardCity.class);
		Card chicagoCard = EasyMock.mock(CardCity.class);
		List<Card> cards = new ArrayList<>(); 
		cards.add(newyorkCard);
		cards.add(chicagoCard);
		
		player.receiveCard(cards);
		EasyMock.expectLastCall().andVoid();
		
		int population = 5;
		EasyMock.expect(player.getHighestPopulationInHand()).andReturn(population);
		EasyMock.expect(player.getFilteredHand(EasyMock.isA(Predicate.class))).andReturn(cards);
		
		EasyMock.replay(player);
		controller.givePlayerCards(cards);
		assertEquals(2, controller.getPlayerHandSize());
		assertEquals(5, controller.getHighestPopulationInHand());
		EasyMock.verify(player);
	}
	
	@Test
	public void testCanPerformTrue() {
		EasyMock.expect(actionMap.containsKey(actionType)).andReturn(true);
		EasyMock.expect(actionMap.get(actionType)).andReturn(action);
		EasyMock.expect(action.canPerform()).andReturn(true);
		
		EasyMock.replay(actionMap, action);		
		assertTrue(controller.canPerform(actionType, false));
		EasyMock.verify(actionMap, action);
	}
	
	@Test
	public void testCanPerformFalse() {
		EasyMock.expect(actionMap.containsKey(actionType)).andReturn(true);
		EasyMock.expect(actionMap.get(actionType)).andReturn(action).times(2);
		EasyMock.expect(action.canPerform()).andReturn(true);
		EasyMock.expect(action.isOncePerTurn()).andReturn(true);
		
		EasyMock.replay(actionMap, action);		
		assertFalse(controller.canPerform(actionType, true));
		EasyMock.verify(actionMap, action);
	}
	
	@Test
	public void testPerform() {
		EasyMock.expect(actionMap.get(actionType)).andReturn(action);
		Runnable completionCallback = EasyMock.createNiceMock(Runnable.class);
		action.perform(completionCallback);
		EasyMock.expectLastCall().andVoid();
		
		EasyMock.replay(actionMap, action);		
		controller.perform(actionType, completionCallback);
		EasyMock.verify(actionMap, action);
	}

}
