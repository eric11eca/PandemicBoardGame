package game.cards;

import java.util.Optional;

import game.City;
import game.Epidemic;
import game.cards.event.CardEvent;
import game.player.PlayerImpl;

public class CardEpidemic implements Card {
	private Epidemic epidemic;

	@Override
	public void addToHand(Deck<Card> hand) {
		epidemic.triggerEpidemic();
	}

	@Override
	public Optional<City> getCity() {
		return Optional.empty();
	}

	@Override
	public Optional<CardEvent> getEvent() {
		return Optional.empty();
	}

}
