package game.player;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.city.City;

public class PlayerImpl implements Player {

	private PlayerRole role;

	private Deck hand;
	private Deck playerDiscard;
	private City location;
	private PlayerInteraction interaction;

	public PlayerImpl(PlayerRole role, City startingLocation, Deck playerDiscard, PlayerInteraction interaction) {
		super();
		this.role = role;
		this.hand = new Deck();
		this.playerDiscard = playerDiscard;
		this.location = startingLocation;
		this.interaction = interaction;
	}

	@Override
	public int getHighestPopulationInHand() {
		int max = 0;
		for (Card card : hand.getFilteredSubDeck(c -> c.getCity().isPresent())) {
			max = Math.max(max, card.getCity().get().getPopulation());
		}
		return max;
	}

	@Override
	public void receiveCard(List<Card> cards) {
		cards.forEach(card -> card.addToHand(hand));
		while (hand.size() > getHandLimit()) {
			List<Card> toDiscard = interaction.selectCardsToDiscard(hand.size() - getHandLimit(), hand.toList(),
					"PLAYER_DISCARD");
			this.discardCards(toDiscard);
		}
	}

//	private void discardHelper() {
//		interaction.selectCardsToDiscard(hand.size() - getHandLimit(), hand.toList(), toDiscard -> {
//			this.discardCards(toDiscard);
//			if (hand.size() > getHandLimit())
//				discardHelper();
//		});
//	}

	@Override
	public void removeCard(Card toRemove) {
		if (!hand.removeCard(toRemove))
			throw new RuntimeException("Discarding a card this player does not own!");
	}

	@Override
	public void discardCard(Card toDiscard) {
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

	@Override
	public final PlayerRole getRole() {
		return role;
	}

	@Override
	public List<Card> getFilteredHand(Predicate<? super Card> filter) {
		return hand.getFilteredSubDeck(filter);
	}

	@Override
	public List<Card> getSharableKnowledgeCards(Player receiver) {
		if (!receiver.getLocation().equals(getLocation()))
			return Collections.emptyList();
		return hand.getFilteredSubDeck(card -> card.getCity().filter(location::equals).isPresent());
	}

	private int getHandLimit() {
		return GameProperty.getInstance().getInt("HAND_LIMIT");
	}

}
