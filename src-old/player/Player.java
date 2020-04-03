package player;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cards.PlayerCard;
import data.Board;
import data.GameColor;
import game.City;
import playerAction.PlayerAction;

public class Player {
	public PlayerData playerData;
	public Board board;

	public Map<Board.ActionName, PlayerAction> playerActions;
	@Deprecated // Remove temporary fields
	public City destination;
	@Deprecated // Remove temporary fields
	public String eventCardName;
	@Deprecated // Remove temporary fields
	public String diseaseTobeTreated;
	@Deprecated // Remove temporary fields
	public PlayerCard cityCard;
	@Deprecated // Remove temporary fields
	public List<PlayerCard> cardsToCureDisease;

	public Player(Board board, PlayerData playerData) {
		this.board = board;
		this.playerData = playerData;

		playerData.action = 4;
	}

	public void receiveCard(PlayerCard playerCard) {
		playerData.hand.put(playerCard.cardName, playerCard);
	}

	public void discardCard() {
		for (int i = 0; i < board.cardToBeDiscard.size(); i++) {
			String cardName = board.cardToBeDiscard.get(i);
			if (playerData.hand.containsKey(cardName)) {
				playerData.hand.remove(cardName);
			}
		}
		board.cardToBeDiscard.clear();
	}

	public void consumeAction() {
		playerData.action -= 1;
	}

	public void useEventCard(String cardName) {
		if (cardName.equals(playerData.specialEventCard)) {
			playerData.specialEventCard = null;
		} else {
			playerData.hand.remove(cardName);
			board.discardEventCards.add(cardName);
		}
		board.eventCardAction.executeEventCard(cardName);
	}

	public void drive(City destination) {
		if (board.dispatcherCase == 1) {
			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
			if (pawnData.location.isNeighbor(destination)) {
				moveTo(destination);
				consumeAction();
			}
		} else if (playerData.location.isNeighbor(destination)) {
			moveTo(destination);
			consumeAction();
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

	public void moveTo(City destination) {
		if (board.dispatcherCase == 1) {
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

	public void charterFlight(City destination) {
		board.cardToBeDiscard.add(playerData.location.getName());
		discardCardAndMoveTo(destination);
		consumeAction();
	}

	public void shuttleFlight(City destination) {
		if (playerData.location.hasResearchStation()) {
			if (destination.hasResearchStation()) {
				moveTo(destination);
				consumeAction();
			}
		}
	}

	public void treat(GameColor diseaseColor) {
		playerData.treatAction.treat(playerData.location, diseaseColor);
		// eradicate(diseaseColor);
		consumeAction();
	}

//	public void eradicate(String diseaseColor) {
//		if (board.remainDiseaseCube.get(diseaseColor) == 24) {
//			board.eradicatedColor.add(diseaseColor);
//		}
//	}

	public void discoverCure(List<PlayerCard> cardsToCureDisease) {
		boolean isResearchStation = playerData.location.hasResearchStation();
		if (isResearchStation) {
			if (playerData.discoverCureModel.discover(cardsToCureDisease)) {
				for (PlayerCard playercard : cardsToCureDisease) {
					board.cardToBeDiscard.add(playercard.cardName);
				}
				consumeAction();
			}
			if (board.curedDiseases.size() == 4) {
				throw new RuntimeException("PlayerWinException");
			}

			// eradicate(cardsToCureDisease.get(0).color);
			discardCard();

		} else {
			throw new RuntimeException("NoStationException");
		}
	}

	public void buildStation() {
		playerData.buildStationModel.buildStation();
		consumeAction();
	}

	public void shareKnowledge(Player playerToShare, PlayerCard cityToShare, boolean isGiving) {
		if (cityToShare.cardType != Board.CardType.CITYCARD) {
			throw new RuntimeException("CantUseEventCardException");
		}
		if (isGiving) {
			giveCard(this, playerToShare, cityToShare);
		} else {
			giveCard(playerToShare, this, cityToShare);
		}
		consumeAction();
	}

	private void giveCard(Player giver, Player receiver, PlayerCard citycard) {
		if (giver.playerData.role != Board.Roles.RESEARCHER
				&& !citycard.cardName.equals(giver.playerData.location.getName())) {
			throw new RuntimeException("CanNotShareKnowledgeException");
		}
		board.cardToBeDiscard.add(citycard.cardName);
		giver.discardCard();
		receiver.receiveCard(citycard);
	}

	public boolean canCharterFlight() {
		Set<String> hand = playerData.hand.keySet();
		String playerLocationCityName = playerData.location.getName();
		return hand.contains(playerLocationCityName);
	}
}
