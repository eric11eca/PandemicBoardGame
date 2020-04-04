package game.player.special;

import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.city.City;
import game.player.AbstractPlayerDecorator;
import game.player.Player;

public class DispatchedPlayer extends AbstractPlayerDecorator {
	private Player dispatcher;

	@Override
	public boolean equals(Object obj) {
		return dispatcher.equals(obj);
	}

	@Override
	public int hashCode() {
		return dispatcher.hashCode();
	}

	@Override
	public String toString() {
		return dispatcher.toString();
	}

	@Override
	public void receiveCard(List<Card> cards) {
		dispatcher.receiveCard(cards);
	}

	@Override
	public void removeCard(Card toRemove) {
		dispatcher.removeCard(toRemove);
	}

	@Override
	public void discardCard(Card toDiscard) {
		dispatcher.discardCard(toDiscard);
	}

	@Override
	public void setLocation(City destination) {
		super.setLocation(destination);
	}

	@Override
	public int getID() {
		return super.getID();
	}

	@Override
	public City getLocation() {
		return super.getLocation();
	}

	@Override
	public List<Card> getFilteredHand(Predicate<? super Card> filter) {
		return dispatcher.getFilteredHand(filter);
	}

	@Override
	public List<Card> getSharableKnowledgeCards(Player receiver) {
		throw new UnsupportedOperationException("Unsupported by DispatchedPlayer");
	}

	public DispatchedPlayer(Player dispatcher, Player delegate) {
		super(delegate);
		this.dispatcher = dispatcher;
	}

}
