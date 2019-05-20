package testPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class TestDispatcherMovingAction {
	Board board;
	Player dispatcher;
	Player scientist;
	PlayerData scientistData;
	PlayerData dispatcherData;

	@Before
	public void setup() {
		board  = new Board();
		City milan = new City();
		milan.cityName = "Milan";
		City paris = new City();
		paris.cityName = "Paris";
		City delhi = new City();
		delhi.cityName = "Delhi";
		
		board.cities.put("Milan", milan);
		board.cities.put("Paris", paris);
		board.cities.put("Delhi", delhi);
		
		dispatcherData = new PlayerData();
		dispatcherData.role = Board.Roles.DISPATCHER;
		dispatcherData.location = delhi;
		dispatcherData.action = 4;
		dispatcherData.hand.put("Milan", new PlayerCard(Board.CardType.CITYCARD, "Milan"));
		dispatcherData.hand.put("Delhi", new PlayerCard(Board.CardType.CITYCARD, "delhi"));
		dispatcher = new Player(board, dispatcherData);
		board.currentPlayers.add(dispatcher);
		
		scientistData = new PlayerData();
		scientistData.role = Board.Roles.SCIENTIST;
		scientistData.location = paris;
		scientistData.action = 4;
		scientist = EasyMock.createMock(Player.class); 
		scientist.board = board;  
		scientist.playerData = scientistData;
		board.currentPlayers.add(scientist);
		board.pawnTobeMoved = 1;
	}

	@Test
	public void testDriveUsingOtherPlayer() {
		board.dispatcherCase = 1;
		City destination = board.cities.get("Milan");
		scientistData.location.neighbors.put("Milan", destination);
		EasyMock.replay(scientist);
		dispatcher.drive(destination);
		assertEquals("Milan", scientistData.location.cityName);
		assertTrue(scientistData.action == 4);
		assertTrue(dispatcherData.action == 3);
		EasyMock.verify(scientist);
	}
	
	@Test
	public void testDirectFlightUsingOtherPlayer() {
		board.dispatcherCase = 1;
		PlayerCard cityCard = dispatcherData.hand.get("Milan");
		EasyMock.replay(scientist);
		dispatcher.directFlight(cityCard);
		assertEquals("Milan", scientistData.location.cityName);
		assertTrue(scientistData.action == 4);
		assertTrue(dispatcherData.action == 3);
		EasyMock.verify(scientist);
	}
	
	@Test
	public void testCharterFlightUsingOtherPlayer(){
		board.dispatcherCase = 1;
		board.cityCardNameCharter = "Delhi";
		EasyMock.replay(scientist);
		dispatcher.charterFlight();
		assertEquals("Delhi", scientistData.location.cityName);
		assertTrue(scientistData.action == 4);
		assertTrue(dispatcherData.action == 3);
		EasyMock.verify(scientist);
	}
	
	@Test 
	public void testShuttleFlightUsingOtherPlayer() {
		board.dispatcherCase = 1;
		board.cities.get("Delhi").researchStation = true;
		board.cities.get("Milan").researchStation = true;
		City destination = board.cities.get("Milan");
		EasyMock.replay(scientist);
		dispatcher.shuttleFlight(destination);
		assertEquals("Milan", scientistData.location.cityName);
		assertTrue(scientistData.action == 4);
		assertTrue(dispatcherData.action == 3);
		EasyMock.verify(scientist);
	}

}
