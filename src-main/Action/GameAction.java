package Action;

import Card.EpidemicCardAction;
import Card.InfectionCardAction;
import Card.Outbreak;
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
		int currentPlayerIdx = board.currentPlayerIndex;
		board.currentPlayer = board.currentPlayers.get(currentPlayerIdx);
	}
	
	public void drawTwoPlayerCards() {
		for(int i = 0; i < 2; i++) {
			PlayerCard playerCard = board.validPlayerCard.get(0);
			board.currentPlayer.receiveCard(playerCard);
			board.validPlayerCard.remove(0);
			if(playerCard.cardType == Board.CardType.EPIDEMIC) {
				epidemic.performeEpidemic();
			}
			board.discardPlayerCard.put(playerCard.cardName, playerCard);
		} 
	}
	
	public void doAction(String actionName) {
		if(actionName.equals("DirectFlight")) {
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
			PlayerCard cityCard = board.currentPlayer.hand.get(board.cityCardNameCharter);
			board.currentPlayer.characterFlight(cityCard);
		} else if (actionName.equals("ShuttleFlight")) {
			City shuttleDestination = board.cities.get(board.shuttleDestinationName);
			board.currentPlayer.shuttleFlight(shuttleDestination);
		}
	}
	
	public void infection() {
		for (int i = 0; i < board.infectionRateTrack.peek(); i++) {
			infectAction.drawOneInfactionCard();
		}
	}
	
	
	
}