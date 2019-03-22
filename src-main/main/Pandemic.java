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
		InitializePlayerData initPlayerData = new InitializePlayerData(board);
		//TODO: getting these two number from gui
		board.playernumber = 0;
		
		int difficulty = 0;
		int epidemicCardNum = 0;
		
		if (board.playernumber == 2) {
			board.initialhandcard = 4;
		} else if (board.playernumber == 3) {
			board.initialhandcard = 3;
		} else if (board.playernumber == 4) {
			board.initialhandcard = 2;
		}
		
		if (difficulty == 1) {
			epidemicCardNum = 4;
		} else if (difficulty == 2) {
			epidemicCardNum = 5;
		} else if (difficulty == 3) {
			epidemicCardNum = 6;
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
		
		initPlayerData.addRole();
		initPlayerData.createPlayers();
		initPlayerData.drawHandCard();
		initPlayerData.sortPlayer();
		
		int validPlayerCardNum = 53 - board.initialhandcard * board.playernumber;
		initBoard.initializeEpidemicCard(validPlayerCardNum, epidemicCardNum);
    }
}
