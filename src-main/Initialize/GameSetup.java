package Initialize;

import Action.GameAction;
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

		initBoard.initializeSpecialEndGameDemo();

		int validPlayerNum = 53 - board.initialhandcard * board.playernumber;
		initBoard.initializeEpidemicCard(validPlayerNum);
		
		initBoard.initializeSpecialEndGameDemo();
		initGame.startCreationofBoard();
	}

	public void oneTurn() {
		gameAction.doAction(board.actionName);
			
		if (board.gameEnd) {
			if (board.playerWin) {
				initGame.gui.gameEnd("Congradulation, You Win!");
			} else {
				initGame.gui.gameEnd("Sorry, You Lose.");
			}
			return;
		}
			
		if (board.currentPlayer.action == 0) {
			try {
				gameAction.drawTwoPlayerCards();
			} catch (RuntimeException e) {
				if(board.currentPlayer.hand.size() <= 7){
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
		board.currentPlayer.action = 4;
		board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);
	}
}
