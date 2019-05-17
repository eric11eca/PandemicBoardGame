package gameAction;

import cardActions.EpidemicCardAction;
import cardActions.InfectionCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;

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
			if(board.validPlayerCards.isEmpty()) {	
				board.playerLose = true;
				board.gameEnd = true;
				return;
			} else {
				playerCard = board.validPlayerCards.get(0);
			}
			if (playerCard.cardType == Board.CardType.EPIDEMIC) {
				epidemic.performEpidemic();
			} else {
				board.currentPlayer.receiveCard(playerCard);
			}
			board.validPlayerCards.remove(0);
		}
		
		if (board.currentPlayer.playerData.hand.size() > 7) {
			throw new RuntimeException("Player hand overflows");
		}
	}


	public void doAction(Board.ActionName actionName) {
		boolean isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		boolean doesChangeLocation = false;
		if (actionName == Board.ActionName.DIRECTFLIGHT) {
			PlayerCard cityCard = board.currentPlayer.playerData.hand.get(board.cityCardNameDirect);
			board.currentPlayer.directFlight(cityCard);
			doesChangeLocation = true;
		} else if (actionName == Board.ActionName.PLAYEVENTCARD) {
			board.currentPlayer.useEventCard(board.eventCardName);
			if(board.eventCardName.equals("Airlift")) {
				doesChangeLocation = true;
			}
		} else if (actionName == Board.ActionName.CUREDISEASE) {
			board.currentPlayer.discoverCure(board.cardsToCureDisease);
		} else if (actionName == Board.ActionName.TREATDISEASE) {
			board.currentPlayer.treat(board.diseaseBeingTreated);
		} else if (actionName == Board.ActionName.DRIVE) {
			City driveDestination = board.cities.get(board.driveDestinationName);
			doesChangeLocation = true;
			board.currentPlayer.drive(driveDestination);
		} else if (actionName == Board.ActionName.CHARTERFLIGHT) {
			board.currentPlayer.charterFlight();
			doesChangeLocation = true;
		} else if (actionName == Board.ActionName.SHUTTLEFLIGHT) {
			City shuttleDestination = board.cities.get(board.shuttleDestinationName);
			board.currentPlayer.shuttleFlight(shuttleDestination);
			doesChangeLocation = true;
		} else if (actionName == Board.ActionName.BUILDRESEARCH) {
			board.currentPlayer.buildStation();
		} else if (actionName == Board.ActionName.SHAREKNOWLEDGE) {
			board.currentPlayer.shareKnowledge();
		}
		if(isMedic && doesChangeLocation) {
			board.currentPlayer.playerData.specialSkill.useSpecialSkill();
		}
	}

	public void infection() {
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infectAction.drawOneInfectionCard();
		}
	}

}