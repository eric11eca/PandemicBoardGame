package game.player;

import java.util.List;
import java.util.function.Consumer;

import game.City;
import game.cards.Card;

public interface PlayerController {

	void drive();

	void directFlight();

	void charterFlight();

	void shuttleFlight();

	void buildResearchStation();

	void treatDisease();

	void giveKnowledge();

	void takeKnowledge();

	void discoverCure();

	void useEventCard();

	void useSpecialSkill();

	void selectPlayerFrom(List<Player> players, Consumer<Player> callback);

	void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback);

	void selectCity(Consumer<City> callback);

}