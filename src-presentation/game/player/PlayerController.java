package game.player;

import java.util.List;

import cards.PlayerCard;
import data.GameColor;
import game.City;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.event.CardEvent;

public interface PlayerController {

	void drive(CardCity card);

	void directFlight(CardCity card);

	void charterFlight(CardCity card);

	void shuttleFlight(CardCity card);

	void buildResearchStation(CardCity card);

	void treatDisease(GameColor diseaseColor);

	void giveKnowledge(Player otherPlayer, CardCity card);

	void takeKnowledge(Player otherPlayer, CardCity card);

	void discoverCure(List<CardCity> cards);

	void useEventCard(Card card);

	void useSpecialSkill();

}