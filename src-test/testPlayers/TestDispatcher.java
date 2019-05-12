package testPlayers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import cards.PlayerCard;
import initialize.Board;
import initialize.City;
import player.Player;
import player.PlayerData;
import playerAction.DispatcherAction;
import playerAction.SpecialSkill;

public class TestDispatcher {
	Board board;
	Player dispatcher;
	Player scientist;
	PlayerData scientistData;
	PlayerData dispatcherData;
	SpecialSkill dispatcherAction;

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
		dispatcherData.hand.put("Milan", new PlayerCard(Board.CardType.CITYCARD, "Milan"));
		dispatcherData.hand.put("Delhi", new PlayerCard(Board.CardType.CITYCARD, "delhi"));
		dispatcherAction = new DispatcherAction(board);
		dispatcherData.specialSkill = dispatcherAction;
		dispatcher = new Player(board, dispatcherData);
		board.currentPlayers.add(dispatcher);
		
		scientistData = new PlayerData();
		scientistData.role = Board.Roles.SCIENTIST;
		scientistData.location = paris;
		scientist = new Player(board, scientistData);
		board.currentPlayers.add(scientist);
		board.pawnTobeMoved = 1;
	}
	
	@Test
	public void testMoveOtherPlayer() {
		board.dispatcherCase = 0;
		board.newLocationName = "Delhi";
		dispatcher.playerData.specialSkill.specialSkill();
		assertEquals("Delhi", scientistData.location.cityName);
	}
	
	@Test
	public void testDriveUsingOtherPlayer() {
		board.dispatcherCase = 1;
		City destination = board.cities.get("Milan");
		scientistData.location.neighbors.put("Milan", destination);
		dispatcher.drive(destination);
		assertEquals("Milan", scientistData.location.cityName);
	}
	
	@Test
	public void testDirectFlightUsingOtherPlayer() {
		board.dispatcherCase = 1;
		PlayerCard cityCard = dispatcherData.hand.get("Milan");
		dispatcher.directFlight(cityCard);
		assertEquals("Milan", scientistData.location.cityName);
	}
	
	@Test
	public void testCharterFlightUsingOtherPlayer(){
		board.dispatcherCase = 1;
		board.cityCardNameCharter = "Delhi";
		dispatcher.charterFlight();
		assertEquals("Delhi", scientistData.location.cityName);
	}
	
	@Test 
	public void testShuttleFlightUsingOtherPlayer() {
		board.dispatcherCase = 1;
		board.cities.get("Delhi").researchStation = true;
		board.cities.get("Milan").researchStation = true;
		City destination = board.cities.get("Milan");
		dispatcher.shuttleFlight(destination);
		assertEquals("Milan", scientistData.location.cityName);
	}
	
	


}
