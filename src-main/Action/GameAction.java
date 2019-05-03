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
		
		if (board.currentPlayer.playerData.hand.size() > 7) {
			throw new RuntimeException("Player hand overflows");
		}
	}


	public void doAction(Board.ActionName actionName) {
		if (actionName == Board.ActionName.DIRECTFLIGHT) {
			PlayerCard cityCard = board.currentPlayer.playerData.hand.get(board.cityCardNameDirect);
			board.currentPlayer.directFlight(cityCard);
		} else if (actionName == Board.ActionName.PLAYEVENTCARD) {
			board.currentPlayer.useEventCard(board.eventCardName);
		} else if (actionName == Board.ActionName.CUREDISEASE) {
			board.currentPlayer.discoverCure(board.cardsToCureDisease);
		} else if (actionName == Board.ActionName.TREATDISEASE) {
			board.currentPlayer.treat(board.diseaseBeingTreated);
		} else if (actionName == Board.ActionName.DRIVE) {
			City driveDestination = board.cities.get(board.driveDestinationName);
			board.currentPlayer.drive(driveDestination);
		} else if (actionName == Board.ActionName.CHARTERFLIGHT) {
			board.currentPlayer.charterFlight();
		} else if (actionName == Board.ActionName.SHUTTLEFLIGHT) {
			City shuttleDestination = board.cities.get(board.shuttleDestinationName);
			board.currentPlayer.shuttleFlight(shuttleDestination);
		} else if (actionName == Board.ActionName.BUILDRESEARCH) {
			board.currentPlayer.buildStation();
		} else if (actionName == Board.ActionName.SHAREKNOWLEDGE) {
			board.currentPlayer.shareKnowledge();
		}
	}

	public void infection() {
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infectAction.drawOneInfectionCard();
		}
	}

}