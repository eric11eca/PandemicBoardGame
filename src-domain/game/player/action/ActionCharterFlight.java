package game.player.action;

import java.util.List;
import java.util.Set;

import game.cards.Card;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionCharterFlight extends Action {
	private CitySet cities;

	public ActionCharterFlight(CitySet cities, Player player, PlayerInteraction interaction) {
		super(player, interaction);
		this.cities = cities;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectOneCardFrom(getCharterFlightCards(), I18n.format("action.charter_flight.select_card"),
				card -> {
					Set<City> allExceptCurrent = cities.getCitiesSatisfying(c -> !c.equals(playerCurrentLocation()));
					interaction.selectCityFrom(allExceptCurrent,
							I18n.format("action.charter_flight.select_destination"), city -> {
								this.performCharterFlightAction(card, city, completionCallback);
							});
				});
	}

	@Override
	public boolean canPerform() {
		return !getCharterFlightCards().isEmpty();
	}

	protected List<Card> getCharterFlightCards() {
		return player().getFilteredHand(this::isCardCurrentLocation);
	}

	protected boolean isCardCurrentLocation(Card card) {
		return card.getCity().filter(playerCurrentLocation()::equals).isPresent();
	}

	protected void performCharterFlightAction(Card usingCard, City flyTo, Runnable completionCallback) {
		player().setLocation(flyTo);
		player().discardCard(usingCard);
		completionCallback.run();
	}

}
