package game.player;

import java.util.List;
import java.util.function.Consumer;

import data.GameColor;
import data.GameProperty;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;

public abstract class PlayerImpl implements PlayerController, Player {
	private static final int HAND_LIMIT = GameProperty.getInstance().getInt("HAND_LIMIT");
	private Deck<Card> hand;
	private Deck<Card> playerDiscard;
	private City location;
	private PlayerInteraction interaction;

	public PlayerImpl() {
		this.hand = new Deck<>();
		this.location = null;
	}

	@Override
	public final void receiveCard(List<Card> cards) {
		cards.forEach(card -> card.addToHand(hand));
		if (hand.size() > HAND_LIMIT) {
			discardHelper();
		}
	}

	private void discardHelper() {
		interaction.selectCardsToDiscard(hand.size() - HAND_LIMIT, hand.toList(), toDiscard -> {
			this.discardCards(toDiscard);
			if (hand.size() > HAND_LIMIT)
				discardHelper();
		});
	}

	@Override
	public final void removeCard(Card toRemove) {
		if (!hand.removeCard(toRemove))
			throw new RuntimeException("Discarding a card this player does not own!");
	}

	@Override
	public final void discardCard(Card toDiscard) {
		removeCard(toDiscard);
		toDiscard.discard(playerDiscard);
	}

	@Override
	public void setLocation(City destination) {
		this.location = destination;
	}

	@Override
	public final City getLocation() {
		return this.location;
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

	/* Other Actions */

	public List<Card> getGiveKnowledgeCards() {
		return hand.getFilteredSubDeck(this::canGiveKnowledgeUsingCard);
	}

	private boolean canGiveKnowledgeUsingCard(Card card) {
		return isCardCurrentLocation(card);
	}

	/* Event Cards */
	public List<Card> getEventCards() {
		return hand.getFilteredSubDeck(card -> card.getEvent().isPresent());
	}

	@Override
	public void giveKnowledge() {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeKnowledge() {
		// TODO Auto-generated method stub

	}

	@Override
	public void discoverCure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void useEventCard() {
		// TODO Auto-generated method stub

	}

	@Override
	public void useSpecialSkill() {
		// TODO Auto-generated method stub

	}
}
