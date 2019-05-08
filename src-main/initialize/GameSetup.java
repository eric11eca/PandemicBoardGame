package initialize;

import java.util.HashMap;

import javax.swing.JOptionPane;

import gameAction.GameAction;
import player.Player;

public class GameSetup {
	public Board board;
	public GameAction gameAction;
	public InitializeGame initGame;
	public InitializeBoard initBoard;
	public InitializePlayerData initPlayerData;
	private HashMap<String,String> messagesToShow;

	public GameSetup() {
		messagesToShow = new HashMap<>();
		messagesToShow.put("NoCityCardException", "You don't have the city card to build a research station here");
		messagesToShow.put("IncorrectNumberOfCardsException", "You don't have the right number of cards");
		messagesToShow.put("CityColorException", "Please only select cards of the same color");
		messagesToShow.put("CityCardException", "Please only select city cards");
		messagesToShow.put("ResearchStationBuilt", "There is already a research station here");
		messagesToShow.put("NoInfectionCards", "Players Lose: There are no more infection cards");
		messagesToShow.put("NoStationException", "You are not at a research Station!!");
		messagesToShow.put("CantUseEventCardException", "Event card cannot be shared");
		messagesToShow.put("OutOfRED", "Players Lose: No more red disease cubes");
		messagesToShow.put("OutOfYELLOW", "Players Lose: No more yellow disease cubes");
		messagesToShow.put("OutOfBLACK", "Players Lose: No more black disease cubes");
		messagesToShow.put("OutOfBLUE", "Players Lose: No more blue disease cubes");
		messagesToShow.put("OutbreakException", "Players Lose: * outbreaks have occured");
	}
	
	public void startGame(){
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

		initBoard.eventCardNames.add("OneQuietNight");
		initBoard.eventCardNames.add("ResilientPopulation");
		initBoard.eventCardNames.add("Forecast");
		initBoard.eventCardNames.add("GovernmentGrant");
		initBoard.eventCardNames.add("Airlift");

		initBoard.initializeWithCityData();
		initBoard.initializeEventCard();
		initBoard.shuffleCards();
		initBoard.initializeRemainDiseaseCube();
		initBoard.initializeDiseaseCube();
		initBoard.initializeInfectionRateTrack();
		initBoard.initializePlayerRoles();
		initBoard.initializeEventCardAction();
		initBoard.initializePlayerTable();
		
		initPlayerData.createPlayers();
		initPlayerData.drawHandCard();
		initPlayerData.sortPlayer();

		City atlanta = board.cities.get("Atlanta");
		atlanta.researchStation = true;

		for (Player player : board.currentPlayers) {
			player.playerData.location = atlanta;
			atlanta.currentRoles.add(player.playerData.role);
		}

		board.cities.put("Atlanta", atlanta);
		
		board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);

		int validPlayerNum = 53 - board.initialhandcard * board.playernumber;
		initBoard.initializeEpidemicCard(validPlayerNum);
		initGame.startCreationofBoard();
	}

	public void oneTurn() {
		try{
			gameAction.doAction(board.actionName);
		} catch (RuntimeException e){
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null,messagesToShow.get(e.getMessage()));
		}
			
		if (board.gameEnd) {
			if (board.playerWin) {
				initGame.gui.gameEnd("Congradulation, You Win!");
			} else {
				initGame.gui.gameEnd("Sorry, You Lose.");
			}
			return;
		}
			
		if (board.currentPlayer.playerData.action == 0) {
			try {
				gameAction.drawTwoPlayerCards();
			} catch (RuntimeException e) {
				if(board.currentPlayer.playerData.hand.size() <= 7){
					changePlayer();
				}
				else{
					initGame.gui.showPlayerHand();
				}
				return;
			}
			changePlayer();
		}
	}

	public void changePlayer() {
		board.currentPlayerIndex++;
		if (board.currentPlayerIndex == board.playernumber){
			board.currentPlayerIndex = 0;
		}
			
		if (board.gameEnd) {
			if (board.playerWin) {
				initGame.gui.gameEnd("Congradulation, You Win!");
			} else {
				initGame.gui.gameEnd("Sorry, You Lose.");
			}
			return;
		}
		
		gameAction.infection();
		board.currentPlayer.playerData.action = 4;
		board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);
	}
}
