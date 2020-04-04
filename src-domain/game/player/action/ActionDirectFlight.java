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
	public void perform() {
		interaction.selectOneCardFrom(getDirectFlightCards(), this::performDirectFlightAction);
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

	protected void performDirectFlightAction(Card usingCard) {
		City city = usingCard.getCity().orElseThrow(RuntimeException::new);
		if (city.equals(playerCurrentLocation()))
			throw new RuntimeException("Flying to same city");
		player().setLocation(city);
		player().discardCard(usingCard);
	}
}
