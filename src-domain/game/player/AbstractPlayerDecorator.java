package game.player;

import java.util.List;

import data.GameColor;
import game.cards.Card;
import game.cards.Deck;
import game.city.City;

public abstract class AbstractPlayerDecorator implements Player {
	protected Player delegate;

	public AbstractPlayerDecorator(Player delegate) {
		super();
		this.delegate = delegate;
	}

	public void receiveCard(List<Card> cards) {
		delegate.receiveCard(cards);
	}

	public void removeCard(Card toDiscard) {
		delegate.removeCard(toDiscard);
	}

	public void moveTo(City destination) {
		delegate.setLocation(destination);
	}

	public List<Card> getDriveCards() {
		return delegate.getDriveCards();
	}

	public List<Card> getDirectFlightCards() {
		return delegate.getDirectFlightCards();
	}

	public List<Card> getCharterFlightCards() {
		return delegate.getCharterFlightCards();
	}

	public List<Card> getShuttleFlightCards() {
		return delegate.getShuttleFlightCards();
	}

	public List<Card> getBuildResearchStationCards() {
		return delegate.getBuildResearchStationCards();
	}

	public boolean canTreatDisease() {
		return delegate.canTreatDisease();
	}

	public List<Card> getGiveKnowledgeCards() {
		return delegate.getGiveKnowledgeCards();
	}

	public List<Card> getDiscoverCureCards(GameColor color) {
		return delegate.getDiscoverCureCards(color);
	}

	public List<Card> getEventCards() {
		return delegate.getEventCards();
	}

	public boolean canUseSpecialSkill() {
		return delegate.canUseSpecialSkill();
	}

	public void discardOneCard(Deck<Card> hand) {
		delegate.discardOneCard(hand);
	}
}
