package game.player;

import java.util.List;

import data.GameColor;
import data.GameProperty;
import game.City;
import game.cards.Card;
import game.cards.Deck;

public abstract class PlayerImpl implements Player {
	private static final int HAND_LIMIT = GameProperty.getInstance().getInt("HAND_LIMIT");
	private Deck<Card> hand;
	private City location;

	public PlayerImpl() {
		this.hand = new Deck<>();
		this.location = null;
	}

	@Override
	public void receiveCard(List<Card> cards) {
		cards.forEach(card -> card.addToHand(hand));
		while (hand.size() > HAND_LIMIT) {
			this.discardOneCard(hand);
		}
	}

	@Override
	public void removeCard(Card toDiscard) {
		if (!hand.removeCard(toDiscard))
			throw new RuntimeException("Discarding a card this player does not own!");
	}

//	@Override
//	public void drive(City destination) {
//		if (board.dispatcherCase == 1) {
//			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
//			if (pawnData.location.isNeighbor(destination)) {
//				moveTo(destination);
//				consumeAction();
//			}
//		} else if (playerData.location.isNeighbor(destination)) {
//			moveTo(destination);
//			consumeAction();
//		}
//	}

//	@Override
//	public void directFlight(PlayerCard cityCard) {
//		if (cityCard.cardType == Board.CardType.CITYCARD) {
//			board.cardToBeDiscard.add(cityCard.cardName);
//			discardCard();
//			consumeAction();
//			City destination = board.cities.get(cityCard.cardName);
//			moveTo(destination);
//		}
//	}

	@Override
	public void moveTo(City destination) {
		this.location = destination;
//		if (board.dispatcherCase == 1) {
//			PlayerData pawnData = board.currentPlayers.get(board.pawnTobeMoved).playerData;
//			pawnData.location = destination;
//			// destination.currentRoles.add(pawnData.role);
//		} else {
//			playerData.location = destination;
//			// destination.currentRoles.add(this.playerData.role);
//		}
	}

//	@Override
//	public void discardCardAndMoveTo(City destination) {
//		discardCard();
//		moveTo(destination);
//	}
//
//	@Override
//	public void charterFlight(City destination) {
//		board.cardToBeDiscard.add(playerData.location.getName());
//		discardCardAndMoveTo(destination);
//		consumeAction();
//	}
//
//	@Override
//	public void shuttleFlight(City destination) {
//		if (playerData.location.hasResearchStation()) {
//			if (destination.hasResearchStation()) {
//				moveTo(destination);
//				consumeAction();
//			}
//		}
//	}
//
//	@Override
//	public void treat(GameColor diseaseColor) {
//		playerData.treatAction.treat(playerData.location, diseaseColor);
//		// eradicate(diseaseColor);
//		consumeAction();
//	}

//	public void eradicate(String diseaseColor) {
//		if (board.remainDiseaseCube.get(diseaseColor) == 24) {
//			board.eradicatedColor.add(diseaseColor);
//		}
//	}

//	@Override
//	public void discoverCure(List<PlayerCard> cardsToCureDisease) {
//		boolean isResearchStation = playerData.location.hasResearchStation();
//		if (isResearchStation) {
//			if (playerData.discoverCureModel.discover(cardsToCureDisease)) {
//				for (PlayerCard playercard : cardsToCureDisease) {
//					board.cardToBeDiscard.add(playercard.cardName);
//				}
//				consumeAction();
//			}
//			if (board.curedDiseases.size() == 4) {
//				throw new RuntimeException("PlayerWinException");
//			}
//
//			// eradicate(cardsToCureDisease.get(0).color);
//			discardCard();
//
//		} else {
//			throw new RuntimeException("NoStationException");
//		}
//	}
//
//	@Override
//	public void buildStation() {
//		playerData.buildStationModel.buildStation();
//		consumeAction();
//	}
//
//	@Override
//	public void shareKnowledge(Player playerToShare, PlayerCard cityToShare, boolean isGiving) {
//		if (cityToShare.cardType != Board.CardType.CITYCARD) {
//			throw new RuntimeException("CantUseEventCardException");
//		}
//		if (isGiving) {
//			giveCard(this, playerToShare, cityToShare);
//		} else {
//			giveCard(playerToShare, this, cityToShare);
//		}
//		consumeAction();
//	}
//
//	private void giveCard(Player giver, Player receiver, PlayerCard citycard) {
//		if (giver.playerData.role != Board.Roles.RESEARCHER
//				&& !citycard.cardName.equals(giver.playerData.location.getName())) {
//			throw new RuntimeException("CanNotShareKnowledgeException");
//		}
//		board.cardToBeDiscard.add(citycard.cardName);
//		giver.discardCard();
//		receiver.receiveCard(citycard);
//	}
	/* Basic Actions */

	@Override
	public boolean canDriveTo(City destination) {
		return location.isNeighboring(destination);
	}

	@Override
	public List<Card> getDirectFlightCards() {
		return hand.getFilteredSubDeck(this::canDirectFlightUsingCard);
	}

	private boolean canDirectFlightUsingCard(Card card) {
		return card.getCity().filter(c -> !c.equals(location)).isPresent();
	}

	@Override
	public List<Card> getCharterFlightCards() {
		return hand.getFilteredSubDeck(this::canCharterFlightUsingCard);
	}

	private boolean canCharterFlightUsingCard(Card card) {
		return isCardCurrentLocation(card);
	}

	@Override
	public List<Card> getShuttleFlightCards() {
		return hand.getFilteredSubDeck(this::canShuttleFlightUsingCard);
	}

	private boolean canShuttleFlightUsingCard(Card card) {
		return location.hasResearchStation() && card.getCity().filter(city -> city.hasResearchStation()).isPresent();
	}

	/* Other Actions */
	@Override
	public List<Card> getBuildResearchStationCards() {
		return hand.getFilteredSubDeck(this::canBuildResearchStationUsingCard);
	}

	private boolean canBuildResearchStationUsingCard(Card card) {
		return !location.hasResearchStation() && isCardCurrentLocation(card);
	}

	@Override
	public boolean canTreatDisease() {
		return location.hasDisease();
	}

	@Override
	public List<Card> getGiveKnowledgeCards() {
		return hand.getFilteredSubDeck(this::canGiveKnowledgeUsingCard);
	}

	private boolean canGiveKnowledgeUsingCard(Card card) {
		return isCardCurrentLocation(card);
	}

	@Override
	public List<Card> getDiscoverCureCards(GameColor color) {
		return hand.getFilteredSubDeck(card -> canDiscoverCureUsingCard(color, card));
	}

	private boolean canDiscoverCureUsingCard(GameColor color, Card card) {
		return card.getCity().filter(c -> c.getColor().equals(color)).isPresent();
	}

	private boolean isCardCurrentLocation(Card card) {
		return card.getCity().filter(location::equals).isPresent();
	}

	/* Event Cards */
	@Override
	public List<Card> getEventCards() {
		return hand.getFilteredSubDeck(card -> card.getEvent().isPresent());
	}
}
