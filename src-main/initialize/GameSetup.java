package initialize;

import java.util.Locale;

import javax.swing.JOptionPane;
import data.Board;
import data.City;
import gameAction.GameAction;
import player.Player;

public class GameSetup {
	public Board board;
	public GameAction gameAction;
	public InitializeGame initGame;
	public InitializeBoard initBoard;
	public InitializePlayerData initPlayerData;

	public GameSetup() {	
	
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

		initBoard.eventCardNames.add(board.messages.getString("OneQuietNight")); 
		initBoard.eventCardNames.add(board.messages.getString("ResilientPopulation")); 
		initBoard.eventCardNames.add(board.messages.getString("Forecast")); 
		initBoard.eventCardNames.add(board.messages.getString("GovernmentGrant")); 
		initBoard.eventCardNames.add(board.messages.getString("Airlift")); 

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
				JOptionPane.showMessageDialog(null,board.messagesToShow.get(e.getMessage()));
		}
			
		if (board.currentPlayer.playerData.action == 0) {
			try {
				gameAction.drawTwoPlayerCards();
			} catch (RuntimeException e) {
				if(e.getMessage().equals("NoPlayerCardsException")) {
					JOptionPane.showMessageDialog(null,board.messagesToShow.get("NoPlayerCardsException"));
				}
				
				if(board.currentPlayer.playerData.hand.size() <= 7){
					changePlayer();
				}
				else{
					if(e.getMessage().equals("PlayerHandOverflow")) {
						initGame.gui.showPlayerHand();
					}
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
		
		gameAction.infection();
		board.currentPlayer.playerData.action = 4;
		board.currentPlayer = board.currentPlayers.get(board.currentPlayerIndex);
	}
	
	public void initializeMessageBundle(String language, String region) {
		Locale defaultLocale = Locale.getDefault();
		
		if(language != null) {
			if(region != null) {
				board.messages = new Messages(new Locale(language, region));
			} else {
				board.messages = new Messages(new Locale(language));
			}
			
		} else  {
			board.messages = new Messages(defaultLocale);
		}
	}
	
	public void initializeMessageToShow() {
		board.messagesToShow.put("NoCityCardsException", board.messages.getString("NoCityCardsException")); 
		board.messagesToShow.put("NoPlayerCardsException", board.messages.getString("NoPlayerCardsException")); 
		board.messagesToShow.put("IncorrectNumberOfCardsException", board.messages.getString("IncorrectNumberOfCardsException")); 
		board.messagesToShow.put("CityColorException", board.messages.getString("CityColorException")); 
		board.messagesToShow.put("CityCardException", board.messages.getString("CityCardException")); 
		board.messagesToShow.put("ResearchStationBuilt", board.messages.getString("ResearchStationBuilt")); 
		board.messagesToShow.put("NoInfectionCards", board.messages.getString("NoInfectionCards")); 
		board.messagesToShow.put("NoStationException", board.messages.getString("NoStationException")); 
		board.messagesToShow.put("CantUseEventCardException", board.messages.getString("CantUseEventCardException")); 
		board.messagesToShow.put("OutOfRED", board.messages.getString("OutOfRED")); 
		board.messagesToShow.put("OutOfYELLOW", board.messages.getString("OutOfYELLOW")); 
		board.messagesToShow.put("OutOfBLACK", board.messages.getString("OutOfBLACK")); 
		board.messagesToShow.put("OutOfBLUE", board.messages.getString("OutOfBLUE")); 
		board.messagesToShow.put("OutbreakException", board.messages.getString("OutbreakException"));
		board.messagesToShow.put("PlayerWinException", board.messages.getString("PlayerWinException")); 
		board.messagesToShow.put("CanNotShareKnowledgeException", board.messages.getString("CanNotShareKnowledgeException")); 
	}
}
