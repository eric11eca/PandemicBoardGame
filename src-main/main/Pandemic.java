package main;
import javax.swing.JButton;
import javax.swing.JPanel;

import Initialize.*;

public class Pandemic {
	
	public static void main(String[] args) {
		InitializeGame game = new InitializeGame();
		Board board = new Board();
		InitializeBoard initializeBoard = new InitializeBoard(board);
		initializeBoard.initializeWithCityData();
		
    }

	
	
		

}
