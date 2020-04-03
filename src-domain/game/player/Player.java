package game.player;

import java.util.List;

import data.GameColor;
import game.City;
import game.cards.Card;
import game.cards.Deck;

public interface Player {

	void receiveCard(List<Card> cards);

	void removeCard(Card toDiscard);

	void moveTo(City destination);

	/* Basic Actions */
	List<Card> getDriveCards();

	List<Card> getDirectFlightCards();

	List<Card> getCharterFlightCards();

	List<Card> getShuttleFlightCards();

	/* Other Actions */
	List<Card> getBuildResearchStationCards();

	boolean canTreatDisease();

	List<Card> getGiveKnowledgeCards();

	List<Card> getDiscoverCureCards(GameColor color);

	/* Event Cards */
	List<Card> getEventCards();

	boolean canUseSpecialSkill();

	void discardOneCard(Deck<Card> hand);

}