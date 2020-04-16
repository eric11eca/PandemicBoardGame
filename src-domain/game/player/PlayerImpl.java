package game.player;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import data.GameProperty;
import game.cards.Card;
import game.cards.Deck;
import game.city.City;

public class PlayerImpl implements Player {

	private final int id;

	private Deck hand;
	private Deck playerDiscard;
	private City location;
	private PlayerInteraction interaction;

	public PlayerImpl(int id, City startingLocation, Deck playerDiscard, PlayerInteraction interaction) {
		super();
		this.id = id;
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
		if (hand.size() > getHandLimit()) {
			discardHelper();
		}
	}

	private void discardHelper() {
		interaction.selectCardsToDiscard(hand.size() - getHandLimit(), hand.toList(), toDiscard -> {
			this.discardCards(toDiscard);
			if (hand.size() > getHandLimit())
				discardHelper();
		});
	}

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
	public final int getID() {
		return id;
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
