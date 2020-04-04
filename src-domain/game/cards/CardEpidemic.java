package game.cards;

import java.util.Optional;

import game.Epidemic;
import game.city.City;
import game.event.Event;
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
	public Optional<Event> getEvent() {
		return Optional.empty();
	}

}
