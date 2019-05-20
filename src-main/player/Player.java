package player;

import java.util.List;
import java.util.Random;

import cardActions.EventCardAction;
import cards.PlayerCard;
import data.Board;
import data.City;

public class Player {
	public PlayerData playerData;
	private Board board;
	public EventCardAction eventCardAction;

	public Player(Board gameBoard, PlayerData playerData) {
		this(gameBoard, new Random());
		this.playerData = playerData;
		playerData.action = 4;
	}

	public Player(Board gameBoard, Random random) {
		board = gameBoard;
	}

	public void receiveCard(PlayerCard playerCard) {
		playerData.hand.put(playerCard.cardName, playerCard);
	}

	public void useEventCard(String cardName) {
		if (cardName.equals(playerData.roleCard)) {
			board.eventCardAction.executeEventCard(cardName);
			board.eventCards.remove(cardName);
			playerData.roleCard = null;
		} else {
			playerData.hand.remove(cardName);
			board.eventCardAction.executeEventCard(cardName);
			board.discardEventCards.add(cardName);
		}
	}

	public void discardCard() {
		for (int i = 0; i < board.cardToBeDiscard.size(); i++) {
			String cardName = board.cardToBeDiscard.get(i);
			if (playerData.hand.containsKey(cardName)) {
				playerData.hand.remove(cardName);
			} else {
				throw new RuntimeException("This card does not exist in the hand");
			}
		}
		board.cardToBeDiscard.clear();
	}

	public void drive(City destination) {
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData; 
			if (pawnData.location.neighbors.containsKey(destination.cityName)) {
				moveTo(destination);
				consumeAction();
			}
		} else if (playerData.location.neighbors.containsKey(destination.cityName)) {
			moveTo(destination);
			consumeAction();
		} else {
			throw new RuntimeException("Invalid destination: Not a neighbour!!");
		}
	}

	public void directFlight(PlayerCard cityCard) {
		if (cityCard.cardType == Board.CardType.CITYCARD) {
			board.cardToBeDiscard.add(cityCard.cardName);
			discardCard();
			consumeAction();
			City destination = board.cities.get(cityCard.cardName);
			moveTo(destination);
		}
	}

	public void consumeAction() {
		playerData.action -= 1;
	}

	public void moveTo(City destination) {
		if(board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData; 
			pawnData.location = destination;
			destination.currentRoles.add(pawnData.role);
		} else {
			playerData.location = destination;
			destination.currentRoles.add(this.playerData.role);
		}
	}

	public void discardCardAndMoveTo(City destination) {
		discardCard();
		moveTo(destination);
	}

	public void charterFlight() {
		String destinationName = board.cityCardNameCharter;
		City destination = board.cities.get(destinationName);
		board.cardToBeDiscard.add(playerData.location.cityName);
		discardCardAndMoveTo(destination);
		consumeAction();
	}

	public void shuttleFlight(City destination) {
		if (playerData.location.researchStation) {
			if (destination.researchStation) {
				moveTo(destination);
				consumeAction();
			} 
		}
	}

	public void treat(String diseaseColor) {
		playerData.treatAction.treat(diseaseColor);
		eradicate(diseaseColor);
		consumeAction();
	}

	public void eradicate(String diseaseColor) {
		if (board.remainDiseaseCube.get(diseaseColor) == 12) {
			board.eradicatedColor.add(diseaseColor);
		}
	}

	public void discoverCure(List<PlayerCard> cardsToCureDisease) {
		boolean isResearchStation = playerData.location.researchStation;
		if (isResearchStation) {
			if (playerData.discoverCure.discoverCure(cardsToCureDisease)) {
				for (PlayerCard playercard : cardsToCureDisease) {
					board.cardToBeDiscard.add(playercard.cardName);
				}
				consumeAction();
			}
			if (board.curedDiseases.size() == 4) {
				throw new RuntimeException("PlayerWinException");
			}
			discardCard();

		} else {
			throw new RuntimeException("NoStationException");
		}
	}

	public void buildStation() {
		playerData.buildStationModel.buildStation();
		consumeAction();
	}

	public void shareKnowledge() {
		if (board.cityToShare.cardType != Board.CardType.CITYCARD) {
			throw new RuntimeException("CantUseEventCardException");
		}
		if (board.isGiving) {
			giveCard(this, board.playerToShare, board.cityToShare);
		} else {
			giveCard(board.playerToShare, this, board.cityToShare);
		}
		consumeAction();
	}

	private void giveCard(Player giver, Player receiver, PlayerCard citycard) {
		if(giver.playerData.role != Board.Roles.RESEARCHER 
				&& !citycard.cardName.equals(giver.playerData.location.cityName)) {
			throw new RuntimeException("CanNotShareKnowledgeException");
		}
		board.cardToBeDiscard.add(citycard.cardName);
		giver.discardCard();
		receiver.receiveCard(citycard);
	}
}
