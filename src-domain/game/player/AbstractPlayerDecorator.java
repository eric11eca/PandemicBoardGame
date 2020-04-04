package game.player;

import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.city.City;

public abstract class AbstractPlayerDecorator implements Player {
	protected Player delegate;

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	public AbstractPlayerDecorator(Player delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int getHighestPopulationInHand() {
		return delegate.getHighestPopulationInHand();
	}

	@Override
	public void receiveCard(List<Card> cards) {
		delegate.receiveCard(cards);
	}

	@Override
	public void removeCard(Card toRemove) {
		delegate.removeCard(toRemove);
	}

	@Override
	public void discardCard(Card toDiscard) {
		delegate.discardCard(toDiscard);
	}

	@Override
	public void setLocation(City destination) {
		delegate.setLocation(destination);
	}

	@Override
	public int getID() {
		return delegate.getID();
	}

	@Override
	public City getLocation() {
		return delegate.getLocation();
	}

	@Override
	public List<Card> getFilteredHand(Predicate<? super Card> filter) {
		return delegate.getFilteredHand(filter);
	}

	@Override
	public List<Card> getSharableKnowledgeCards(Player receiver) {
		return delegate.getSharableKnowledgeCards(receiver);
	}

}
