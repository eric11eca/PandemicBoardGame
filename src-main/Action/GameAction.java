package Action;

import Card.EpidemicCardAction;
import Card.InfectionCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public class GameAction {
	Board board;
	EpidemicCardAction epidemic;
	InfectionCardAction infectAction;

	public GameAction(Board gameBoard) {
		board = gameBoard;
		epidemic = new EpidemicCardAction(board);
		infectAction = new InfectionCardAction(board);
	}

	public void drawTwoPlayerCards() {
		for (int i = 0; i < 2; i++) {
			PlayerCard playerCard = null;
			if(board.validPlayerCard.isEmpty()) {	
				board.playerLose = true;
				board.gameEnd = true;
				return;
			} else {
				playerCard = board.validPlayerCard.get(0);
			}
			if (playerCard.cardType == Board.CardType.EPIDEMIC) {
				epidemic.performEpidemic();
				board.validPlayerCard.remove(0);
			} else {
				board.currentPlayer.receiveCard(playerCard);
				board.validPlayerCard.remove(0);
				board.discardPlayerCard.put(playerCard.cardName, playerCard);
			}
		}
		
		if (board.currentPlayer.hand.size() > 7) {
			throw new RuntimeException("Player hand overflows");
		}
	}

	public void doAction(String actionName) {
		if (actionName.equals("DirectFlight")) {
			PlayerCard cityCard = board.currentPlayer.hand.get(board.cityCardNameDirect);
			board.currentPlayer.directFlight(cityCard);
		} else if (actionName.equals("PlayEventCard")) {
			board.currentPlayer.useEventCard(board.eventCardName);
		} else if (actionName.equals("CureDisease")) {
			board.currentPlayer.discoverCure(board.cardsToCureDisease);
		} else if (actionName.equals("TreatDisease")) {
			board.currentPlayer.treat(board.diseaseBeingTreated);
		} else if (actionName.equals("Drive")) {
			City driveDestination = board.cities.get(board.driveDestinationName);
			board.currentPlayer.drive(driveDestination);
		} else if (actionName.equals("CharterFlight")) {
			board.currentPlayer.charterFlight();
		} else if (actionName.equals("ShuttleFlight")) {
			City shuttleDestination = board.cities.get(board.shuttleDestinationName);
			board.currentPlayer.shuttleFlight(shuttleDestination);
		} else if (actionName.equals("BuildResearch")) {
			board.currentPlayer.buildStation();
		} else if (actionName.equals("ShareKnowledge")) {
			board.currentPlayer.shareKnowledge();
		}
	}

	public void infection() {
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infectAction.drawOneInfectionCard();
		}
	}

}