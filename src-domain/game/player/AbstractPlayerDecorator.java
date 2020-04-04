package game.player;

import java.util.List;
import java.util.function.Predicate;

import game.cards.Card;
import game.city.City;

public abstract class AbstractPlayerDecorator implements Player {
	protected Player delegate;

	public AbstractPlayerDecorator(Player delegate) {
		super();
		this.delegate = delegate;
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
