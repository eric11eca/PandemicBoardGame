package game.player.action;

import java.util.List;

import game.cards.Card;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionDirectFlight extends Action {
	public ActionDirectFlight(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectOneCardFrom(getDirectFlightCards(), "action.direct_flight.select_destination", card -> {
			performDirectFlightAction(card, completionCallback);
		});
	}

	@Override
	public boolean canPerform() {
		return !getDirectFlightCards().isEmpty();
	}

	protected List<Card> getDirectFlightCards() {
		return player().getFilteredHand(this::canDirectFlightUsingCard);
	}

	protected boolean canDirectFlightUsingCard(Card card) {
		return card.getCity().filter(c -> !c.equals(playerCurrentLocation())).isPresent();
	}

	protected void performDirectFlightAction(Card usingCard, Runnable completionCallback) {
		City city = usingCard.getCity().get();
		assert !city.equals(playerCurrentLocation());
		player().setLocation(city);
		player().discardCard(usingCard);
		completionCallback.run();
	}
}
