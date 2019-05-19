package gameAction;

import cardActions.EpidemicCardAction;
import cardActions.InfectionCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;
import player.PlayerData;

public class GameAction {
	Board board;
	EpidemicCardAction epidemic;
	InfectionCardAction infectAction;
	public boolean doesChangeLocation = false;
	public boolean isMedic;

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
		Player player = board.currentPlayer;
		isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		
		switch (actionName) {
			default:
				break;
			case DIRECTFLIGHT: 
				String cardName = board.cityCardNameDirect;
				PlayerCard cityCard = player.playerData.hand.get(cardName);
				player.directFlight(cityCard);
				doesChangeLocation = true;
				break;
			case PLAYEVENTCARD:
				player.useEventCard(board.eventCardName);
				if(board.eventCardName.equals("Airlift")) {
					doesChangeLocation = true;
				}
				break;
			case CUREDISEASE:
				player.discoverCure(board.cardsToCureDisease);
				break;
			case TREATDISEASE:
				player.treat(board.diseaseBeingTreated);
				break;
			case DRIVE:
				City driveDestination = board.cities.get(board.driveDestinationName);
				doesChangeLocation = true;
				player.drive(driveDestination);
			case CHARTERFLIGHT:
				player.charterFlight();
				doesChangeLocation = true;
			case SHUTTLEFLIGHT:  
				City shuttleDestination = board.cities.get(board.shuttleDestinationName);
				player.shuttleFlight(shuttleDestination);
				doesChangeLocation = true;
			case BUILDRESEARCH: 
				player.buildStation();
			case SHAREKNOWLEDGE:
				player.shareKnowledge();
		}
		
		if(isMedic && doesChangeLocation) {
			board.currentPlayer.playerData.specialSkill.specialSkill();
		}
	}

	public void infection() {
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infectAction.drawOneInfectionCard();
		}
	}

}