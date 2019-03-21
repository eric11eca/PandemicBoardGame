package TestInitializeBoard;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.InitializeBoard;

public class TestInitializeEpiDemicCard {
	InitializeBoard initializeBoard;
	Board board;
	ThreadLocalRandom random;
	List<PlayerCard> validPlayerCard;
	
	@Before
	public void setup(){
		board = new Board();
		initializeBoard = new InitializeBoard(board);
		random = EasyMock.mock(ThreadLocalRandom.class);	
		validPlayerCard = initializeBoard.board.validPlayerCard;
		
		for (int i = 0; i < 48; i++) {
			validPlayerCard.add(new PlayerCard(Board.CardType.CITYCARD, ""));
		}
		
		for (int i = 0; i < 5; i++) {
			validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, ""));
		}
		
		Collections.shuffle(validPlayerCard);
	}
	@Test
	public void testLastDeckWhenOddDivideEven() {
		EasyMock.expect(random.nextInt(0, 7)).andReturn(5);
		EasyMock.expect(random.nextInt(7, 14)).andReturn(11);
		EasyMock.expect(random.nextInt(14, 21)).andReturn(20);
		EasyMock.expect(random.nextInt(21, 28)).andReturn(24);
		EasyMock.expect(random.nextInt(28, 35)).andReturn(33);
		EasyMock.expect(random.nextInt(35, 45)).andReturn(43);

		EasyMock.replay(random);
		initializeBoard.random = random;

		initializeBoard.initializeEpidemicCard(45, 6);			
		assertEquals(Board.CardType.EPIDEMIC, validPlayerCard.get(48).cardType);
		
		EasyMock.verify(random);
	}
	
	@Test
	public void testFirstDeckWhenOddDivideEven() {
		EasyMock.expect(random.nextInt(0, 7)).andReturn(5);
		EasyMock.expect(random.nextInt(7, 14)).andReturn(11);
		EasyMock.expect(random.nextInt(14, 21)).andReturn(20);
		EasyMock.expect(random.nextInt(21, 28)).andReturn(24);
		EasyMock.expect(random.nextInt(28, 35)).andReturn(33);
		EasyMock.expect(random.nextInt(35, 45)).andReturn(43);

		EasyMock.replay(random);
		initializeBoard.random = random;

		initializeBoard.initializeEpidemicCard(45, 6);		
		assertEquals(Board.CardType.EPIDEMIC, validPlayerCard.get(5).cardType);
		
		EasyMock.verify(random);
	}
	
	@Test
	public void testLastDeckWhenEvenDivideOdd() {
		EasyMock.expect(random.nextInt(0, 9)).andReturn(5);
		EasyMock.expect(random.nextInt(9, 18)).andReturn(17);
		EasyMock.expect(random.nextInt(18, 27)).andReturn(20);
		EasyMock.expect(random.nextInt(27, 36)).andReturn(34);
		EasyMock.expect(random.nextInt(36, 44)).andReturn(43);

		EasyMock.replay(random);
		initializeBoard.random = random;

		initializeBoard.initializeEpidemicCard(44, 5);		
		assertEquals(Board.CardType.EPIDEMIC, validPlayerCard.get(47).cardType);
		
		EasyMock.verify(random);
	}
	
	@Test
	public void testFirstDeckWhenEvenDivideOdd() {
		EasyMock.expect(random.nextInt(0, 9)).andReturn(7);
		EasyMock.expect(random.nextInt(9, 18)).andReturn(17);
		EasyMock.expect(random.nextInt(18, 27)).andReturn(20);
		EasyMock.expect(random.nextInt(27, 36)).andReturn(34);
		EasyMock.expect(random.nextInt(36, 44)).andReturn(43);

		EasyMock.replay(random);
		initializeBoard.random = random;
		
		initializeBoard.initializeEpidemicCard(44, 5);
		assertEquals(Board.CardType.EPIDEMIC, validPlayerCard.get(7).cardType);
		
		EasyMock.verify(random);
	}
}
