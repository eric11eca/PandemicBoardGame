package Player;

import java.util.List;
import java.util.Random;

import Card.EventCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public class Player {
	public PlayerData playerData;
	public SpecialSkill specialSkill;
	private Board board;
	private EventCardAction eventCardAction;
	
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

	public boolean useEventCard(String cardName) {
		boolean cardUsed = false;
		if (cardName.equals(playerData.specialEventCard.cardName)) {
			cardUsed = eventCardAction.executeEventCard(cardName);
			if (cardUsed) {
				playerData.specialEventCard = null;
			}
		} else {
			eventCardAction.executeEventCard(cardName);
		}
		return cardUsed;
	}

	public void discardCard() {
		for (int i = 0; i < board.cardToBeDiscard.size(); i++) {
			String cardName = board.cardToBeDiscard.get(i);
			if (playerData.hand.containsKey(cardName)) {
				PlayerCard playerCard = playerData.hand.get(cardName);
				playerData.hand.remove(cardName);
				board.discardPlayerCard.put(cardName, playerCard);
			} else {
				throw new RuntimeException("This card does not exist in the hand");
			}
		}
		board.cardToBeDiscard.clear();
	}

	public void drive(City destination) {
		if (playerData.location.neighbors.containsKey(destination.cityName)) {
			moveTo(destination);
			consumeAction();
		} else {
			throw new RuntimeException("Invalid destination: Not a neighbour!!");
		}
	}

	public void directFlight(PlayerCard cityCard) {
		if (cityCard.cardName.equals(playerData.location.cityName)) {
			throw new IllegalArgumentException("Cannot direct flight to current city");
		} else if (cityCard.cardType == Board.CardType.CITYCARD) {
			board.cardToBeDiscard.add(cityCard.cardName);
			discardCard();
			consumeAction();
			City destination = board.cities.get(cityCard.cardName);
			moveTo(destination);
		} else {
			throw new IllegalArgumentException("Illegal Argument Type");
		}
	}

	public void consumeAction() {
		if (playerData.action <= 0) {
			throw new RuntimeException("NO MORE ACTIONS!");
		}
		playerData.action -= 1;
	}
	
	public void moveTo(City destination) {
		playerData.location = destination;
		destination.currentRoles.add(this.playerData.role);
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
				playerData.location = destination;
				destination.currentRoles.add(this.playerData.role);
				consumeAction();
			} else {
				throw new RuntimeException("Invalid shuttle flight: Destination doesn't have the station.");
			}
		} else {
			throw new RuntimeException("Invalid shuttle flight: Current location doesn't hava the station.");
		}
	}

	public void treat(String diseaseColor) {
		int numOfDiseaseCubes = playerData.location.diseaseCubes.get(diseaseColor);
		if (numOfDiseaseCubes == 0) {
			throw new RuntimeException("The number of " + diseaseColor + "Cube is zero");
		}
		int removeCounts = 1;
		if (board.curedDiseases.contains(diseaseColor)) {
			removeCounts = numOfDiseaseCubes;
		}
		playerData.location.diseaseCubes.put(diseaseColor, numOfDiseaseCubes - removeCounts);
		consumeAction();
	}

	public void discoverCure(List<PlayerCard> cardsToCureDisease) {
		if (isResearchStation()) {
			if (playerData.discoverCure.discoverCure(cardsToCureDisease)) {
				for (PlayerCard playercard : cardsToCureDisease) {
					board.cardToBeDiscard.add(playercard.cardName);
				}
				consumeAction();
			}
			
			if (board.curedDiseases.size() == 4) {
				board.gameEnd = true;
				board.playerWin = true;
				return;
			}
			discardCard();

		} else {
			throw new RuntimeException("NoStationException");
		}
	}

	private boolean isResearchStation() {
		return playerData.location.researchStation;
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
			if (checkHand(playerData, board.cityToShare)) {
				giveCard(this, board.playerToShare, board.cityToShare);
			} else {
				throw new RuntimeException("You don't have this city card");
			}
		} else {
			if (checkHand(board.playerToShare.playerData, board.cityToShare)) {
				giveCard(board.playerToShare, this, board.cityToShare);
			} else {
				throw new RuntimeException("Your friend doesn't have this city card");
			}
		}
		consumeAction();
	}

	private boolean checkHand(PlayerData playerData, PlayerCard citycard) {
		return playerData.hand.containsKey(citycard.cardName);
	}

	private void giveCard(Player giver, Player receiver, PlayerCard citycard) {
		board.cardToBeDiscard.add(citycard.cardName);
		giver.discardCard();
		receiver.receiveCard(citycard);
	}
}
