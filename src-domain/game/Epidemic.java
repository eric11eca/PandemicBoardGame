package game;

import java.util.function.Supplier;

import game.cards.CardCity;
import game.cards.Deck;
import game.disease.GameCubePool;

public class Epidemic {
	private Deck<CardCity> infectionCards;
	private Deck<CardCity> infectionDiscard;
	private Game game;
	private Supplier<City> quarantineSpecialistLocation;
	private GameCubePool gameCubePool;

	public Epidemic(Deck<CardCity> infectionCards, Deck<CardCity> infectionDiscard, Game game,
			Supplier<City> quarantineSpecialistLocation, GameCubePool gameCubePool) {
		super();
		this.infectionCards = infectionCards;
		this.infectionDiscard = infectionDiscard;
		this.game = game;
		this.quarantineSpecialistLocation = quarantineSpecialistLocation;
		this.gameCubePool = gameCubePool;
	}

	public void triggerEpidemic() {
		game.increaseInfectionRate();
		epidemicInfect();
		epidemicIntensify();
	}

	private void epidemicInfect() {
		City city = determineCity();
		if (!gameCubePool.isDiseaseEradicated(city.getColor()))
			city.epidemicInfect(quarantineSpecialistLocation.get());
		infectionDiscard.putOnTop(new CardCity(city));
	}

	private City determineCity() {
		CardCity card = infectionCards.takeBottomCard();
		return card.getCity().orElseThrow(() -> new RuntimeException("Infection card has no city"));
	}

	private void epidemicIntensify() {
		infectionDiscard.shuffle();
		infectionDiscard.forEach(infectionCards::putOnTop);
		infectionDiscard.clear();
	}
}
