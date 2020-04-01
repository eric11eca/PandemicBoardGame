package gameAction;

import java.util.List;

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
			if (board.validPlayerCards.isEmpty()) {
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
	
	public void shuttleFlight(String chosenCity) {
		City shuttleDestination = board.cities.get(chosenCity);
		board.currentPlayer.shuttleFlight(shuttleDestination);
		doesChangeLocation = true;
		checkSpeicialSkill();
	}
	
	public void directFlight(String cityCardName) {
		Player player = board.currentPlayer;
		PlayerCard cityCard = player.playerData.hand.get(cityCardName);
		player.directFlight(cityCard);
		doesChangeLocation = true;
		checkSpeicialSkill();
	}
	
	public void drive(String chosenCity) {
		City driveDestination = board.cities.get(chosenCity);
		board.currentPlayer.drive(driveDestination);
		doesChangeLocation = true;
		checkSpeicialSkill();
	}
	
	public void charterFlight(String chosenCity) {
		City charterDestination = board.cities.get(chosenCity);
		board.currentPlayer.charterFlight(charterDestination);
		doesChangeLocation = true;
		checkSpeicialSkill();
	}
	
	public void playEventCard(String eventCardName) {
		board.currentPlayer.useEventCard(eventCardName);
		if (eventCardName.equals(board.messages.getString("Airlift"))) {
			doesChangeLocation = true;
		}
		checkSpeicialSkill();
	}
	
	public void cureDisease(List<PlayerCard> cardsToCureDisease) {
		board.currentPlayer.discoverCure(cardsToCureDisease);
		checkSpeicialSkill();
	}
	
	public void treatDisease(String diseaseBeingTreated) {
		board.currentPlayer.treat(diseaseBeingTreated);
		checkSpeicialSkill();
	}
	
	public void buildResearchStation() {
		board.currentPlayer.buildStation();
		checkSpeicialSkill();
	}
	
	public void sharreKnowledge(Player playerToShare, PlayerCard cityToShare, boolean isGiving) {
		board.currentPlayer.shareKnowledge(playerToShare, cityToShare, isGiving);
		checkSpeicialSkill();
	}
	
	private void checkSpeicialSkill() {
		boolean isMedic = (board.currentPlayer.playerData.role == Board.Roles.MEDIC);
		if (isMedic && doesChangeLocation) {
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