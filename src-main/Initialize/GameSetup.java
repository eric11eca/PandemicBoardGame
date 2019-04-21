package Initialize;

import java.awt.Color;
import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import Action.GameAction;
import Panel.GUI;
import Player.Player;

public class GameSetup {
	public Board board;
	public GameAction gameAction;
	public InitializeGame initGame;
	public InitializeBoard initBoard;
	public InitializePlayerData initPlayerData;

	public GameSetup() {
		board = new Board();
		gameAction = new GameAction(board);
		initGame = new InitializeGame(board, this);
		initBoard = new InitializeBoard(board);
		initPlayerData = new InitializePlayerData(board);

	}

	public void startGameSetup() {
		if (board.playernumber == 2) {
			board.initialhandcard = 4;
		} else if (board.playernumber == 3) {
			board.initialhandcard = 3;
		} else if (board.playernumber == 4) {
			board.initialhandcard = 2;
		}

		initBoard.eventCardNames.add("One Quiet Night");
		initBoard.eventCardNames.add("Resilient Population");
		initBoard.eventCardNames.add("Forecast");
		initBoard.eventCardNames.add("Government Grant");
		initBoard.eventCardNames.add("Airlift");

		
		initBoard.initializeWithCityData();
		initBoard.initializeEventCard();
		initBoard.shuffleCards();
		initBoard.initializeRemainDiseaseCube();
		initBoard.initializeDiseaseCube();
		initBoard.initializeInfectionRateTrack();
		initBoard.initializeRoleDeck();
		initBoard.initializeCurrentPlayers();

		initPlayerData.drawHandCard();
		initPlayerData.sortPlayer();

		City atlanta = board.cities.get("Atlanta");
		atlanta.researchStation = true;

		for (Player player : board.currentPlayers) {
			player.location = atlanta;
		}

		board.cities.put("Atlanta", atlanta);
		
		board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);
	

		int validPlayerNum = 53 - board.initialhandcard * board.playernumber;
		initBoard.initializeEpidemicCard(validPlayerNum);
		initGame.startCreationofBoard();
	}

	public void oneTurn() {
			JPanel messageBoard = new JPanel();
			ArrayList<String> messages = new ArrayList<>();
			initGame.gui.removePanel(messageBoard);
			initGame.gui.updateImage();
			
			gameAction.doAction(board.actionName);
			
			String doingActionMessage = "\n Player doing action now."; 
		    String currentAction = MessageFormat.format("\n Current action: {0}", 
														board.currentPlayer.action);
			messages.add(doingActionMessage);
			messages.add(currentAction);
			initGame.gui.displayMessage(messages, messageBoard);
			
			if (board.gameEnd) {
				if (board.playerWin) {
					initGame.gui.gameEnd(GUI.WINING_MESSAGE);
				} else {
					initGame.gui.gameEnd(GUI.LOSING_MESSAGE);
				}
				return;
			}
			
			if (board.currentPlayer.action == 0) {
				String drawingCards = "\n Player drawing cards now.";
				messages.add(drawingCards);
				initGame.gui.displayMessage(messages, messageBoard);				
				System.out.println("hand before drawing: " + board.currentPlayer.hand.keySet().toString());
				
				board.currentPlayer.action = 4;
				board.currentPlayerIndex++;
				if (board.currentPlayerIndex == board.playernumber){
					board.currentPlayerIndex = 0;
				}
				
				try {
					gameAction.drawTwoPlayerCards();
				} catch (RuntimeException e) {
					initGame.gui.showPlayerHand();
				}
				
				System.out.println("hand size: " + board.currentPlayer.hand.size());
				System.out.println("hand after drawing: " + board.currentPlayer.hand.keySet().toString());
				
				if (board.gameEnd) {
					if (board.playerWin) {
						System.out.println("Players Win!");
					} else {
						System.out.println("Player Losses!");
					}
					return;
				}
				
				gameAction.infection();
				board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);
			}
	}

}
