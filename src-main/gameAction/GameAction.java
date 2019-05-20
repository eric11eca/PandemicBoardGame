package gameAction;

import cardActions.EpidemicCardAction;
import cardActions.InfectionCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;
import player.Player;

public class GameAction {
	Board board;
	public EpidemicCardAction epidemic;
	InfectionCardAction infectAction;
	public boolean doesChangeLocation = false;

	public GameAction(Board gameBoard) {
		board = gameBoard;
		epidemic = new EpidemicCardAction(board);
		infectAction = new InfectionCardAction(board);
	}

	public void drawTwoPlayerCards() {
		for (int i = 0; i < 2; i++) {
			PlayerCard playerCard = null;	
			if(board.validPlayerCards.isEmpty()) {	
				throw new RuntimeException("NoPlayerCardsException");
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
		boolean isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		doesChangeLocation = false;
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
				if(board.eventCardName.equals(board.messages.getString("Airlift"))) {
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
				break;
			case CHARTERFLIGHT:
				player.charterFlight();
				doesChangeLocation = true;
				break;
			case SHUTTLEFLIGHT:  
				City shuttleDestination = board.cities.get(board.shuttleDestinationName);
				player.shuttleFlight(shuttleDestination);
				doesChangeLocation = true;
				break;
			case BUILDRESEARCH: 
				player.buildStation();
				break;
			case SHAREKNOWLEDGE:
				player.shareKnowledge();
				break;
		}
		
		if(isMedic && doesChangeLocation) {
			board.currentPlayer.playerData.specialSkill.useSpecialSkill();
		}
	}

	public void infection() {
		if (board.inQueitNight) {
			board.inQueitNight = false;
			return;
		}
		for (int i = 0; i < board.infectionRateTracker.peek(); i++) {
			infectAction.drawOneInfectionCard();
		}
	}

}