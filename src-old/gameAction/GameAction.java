package gameAction;

import cardActions.EpidemicCardAction;
import cardActions.InfectionCardAction;
import cards.PlayerCard;
import data.Board;
import data.Board.ActionName;
import player.Player;
import playerAction.PlayerAction;

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
			throw new RuntimeException("PlayerHandOverflow");
		}
	}

	public void doAction(Board.ActionName actionName) {
		Player player = board.currentPlayer;
		boolean isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		doesChangeLocation = false;
		
		PlayerAction action = player.getPlayerAction(actionName);
		doesChangeLocation = action.executeAction();
		if (actionName == ActionName.PLAYEVENTCARD) {
			if (board.eventCardName.equals("Airlift")) {
				doesChangeLocation = true;
			}
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