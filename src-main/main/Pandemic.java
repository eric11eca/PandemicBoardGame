package main;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import Initialize.*;

public class Pandemic {
	
	public static void main(String[] args) {
		
		Board board = new Board();
		InitializeGame initGame = new InitializeGame();
		InitializeBoard initBoard = new InitializeBoard(board);
		
		//TODO: getting these two number from gui
		int playerNum = 0;
		int difficulty = 0;

		int EpidemicCardNum = 0;
		int handSize= 0;
		
		if (playerNum == 2) {
			handSize = 4;
		} else if (playerNum == 3) {
			handSize = 3;
		} else if (playerNum == 4) {
			handSize = 2;
		}
		
		if (difficulty == 1) {
			EpidemicCardNum = 4;
		} else if (difficulty == 2) {
			EpidemicCardNum = 5;
		} else if (difficulty == 3) {
			EpidemicCardNum = 6;
		}
		
		initBoard.eventCardNames.add("One Quiet Night");
		initBoard.eventCardNames.add("Resilient Population");
		initBoard.eventCardNames.add("Forecast");
		initBoard.eventCardNames.add("Government Grant");
		initBoard.eventCardNames.add("Airlift");
		
		initBoard.initializeWithCityData();
		initBoard.initializeEventCard();
		initBoard.shuffleCards();
		initBoard.initializeDiseaseCube();
    }

	
	
		

}
