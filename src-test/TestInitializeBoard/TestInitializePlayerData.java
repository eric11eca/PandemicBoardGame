package TestInitializeBoard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.InitializeBoard;
import Initialize.InitializePlayerData;

public class TestInitializePlayerData {
	InitializePlayerData initializePlayerData;
	Board board;
	
	@Before
	public void setup(){
		board = new Board();
		board.playernumber = 2;
		initializePlayerData = new InitializePlayerData(board);
	}
	@Test
	public void testAddRoles(){
		initializePlayerData.addRole();
		assertEquals(7,board.totalRoles.size());
	}
	
	@Test
	public void testCreatePlayers(){
		initializePlayerData.addRole();		
		initializePlayerData.createPlayers();
		assertEquals(2,board.currentPlayers.size());
	}
	
	
	@Test
	public void testdrawHandCardWithTwoPlayer(){
		board.initialhandcard = 4;
		InitializeBoard InitializeBoard = new InitializeBoard(board);
		initializePlayerData.addRole();		
		initializePlayerData.createPlayers();
		InitializeBoard.initializeWithCityData();
		InitializeBoard.initializeEventCard();
		InitializeBoard.shuffleCards();
		initializePlayerData.drawHandCard();	
		assertEquals(4, board.currentPlayers.get(0).hand.size());
		assertEquals(4, board.currentPlayers.get(1).hand.size());
	}
	
	@Test
	public void testSortPlayerByPopulation(){
		board.initialhandcard = 4;
		InitializeBoard InitializeBoard = new InitializeBoard(board);
		initializePlayerData.addRole();		
		initializePlayerData.createPlayers();
		InitializeBoard.initializeWithCityData();
		InitializeBoard.initializeEventCard();
		InitializeBoard.shuffleCards();
		initializePlayerData.drawHandCard();	
		initializePlayerData.sortPlayer();
		assertTrue(initializePlayerData.populationSum(board.currentPlayers.get(0))
				> initializePlayerData.populationSum(board.currentPlayers.get(1)));
	}
	@Test
	public void testSortPlayerByPopulationThreePlayer(){
		board.playernumber = 3;
		board.initialhandcard = 3;
		InitializeBoard InitializeBoard = new InitializeBoard(board);
		initializePlayerData.addRole();		
		initializePlayerData.createPlayers();
		InitializeBoard.initializeWithCityData();
		InitializeBoard.initializeEventCard();
		InitializeBoard.shuffleCards();
		initializePlayerData.drawHandCard();	
		initializePlayerData.sortPlayer();
		assertTrue(initializePlayerData.populationSum(board.currentPlayers.get(0))
				> initializePlayerData.populationSum(board.currentPlayers.get(1)));
		assertTrue(initializePlayerData.populationSum(board.currentPlayers.get(1))
				> initializePlayerData.populationSum(board.currentPlayers.get(2)));
		
	}
}