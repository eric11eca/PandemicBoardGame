package Initialize;

import Player.Player;

public class GameSetup {

	public Board board;
	public InitializeGame initGame;
	public InitializeBoard initBoard;
	public InitializePlayerData initPlayerData;

	public GameSetup() {
		board = new Board();
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

		initPlayerData.addRole();
		initPlayerData.createPlayers();
		initPlayerData.drawHandCard();
		initPlayerData.sortPlayer();

		City atlanta = board.cities.get("Atlanta");
		atlanta.researchStation = true;

		for (Player player : board.currentPlayers) {
			player.location = atlanta;
		}

		board.cities.put("Atlanta", atlanta);

		int validPlayerNum = 53 - board.initialhandcard * board.playernumber;
		initBoard.initializeEpidemicCard(validPlayerNum);
		initGame.startCreationofBoard();

	}

}
