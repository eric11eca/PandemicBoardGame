package TestInitializeBoard;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
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
		initializePlayerData.createPlayers();
		assertEquals(2,board.currentPlayers.size());
	}
	
	
	
	@Test
	public void testdrawHandCardWithTwoPlayer(){
		board.initialhandcard = 4;
		initializePlayerData.drawHandCard();
		assertEquals(4, board.currentPlayers.get(0).hand);
		assertEquals(4, board.currentPlayers.get(1).hand);

//		for(int i = 0; i < board.playernumber; i++){
//			for(int j = 0; j < board.initialhandcard; j++){
//				PlayerCard playercard = board.validPlayerCard.remove(valid)
//				board.currentPlayers.get(i).hand.add(); // the top card
//			}
//		}
	}
}
