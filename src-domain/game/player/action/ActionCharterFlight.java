package game.player.action;

import java.util.List;
import java.util.Set;

import game.cards.Card;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionCharterFlight extends Action {
	private CitySet cities;

	public ActionCharterFlight(CitySet cities, Player player, PlayerInteraction interaction) {
		super(player, interaction);
		this.cities = cities;
	}

	@Override
	public void perform() {
		interaction.selectOneCardFrom(getCharterFlightCards(), card -> {
			Set<City> allExceptCurrent = cities.getCitiesSatisfying(c -> !c.equals(player.getLocation()));
			interaction.selectCityFrom(allExceptCurrent, city -> this.performCharterFlightAction(card, city));
		});
	}

	@Override
	public boolean canPerform() {
		return !getCharterFlightCards().isEmpty();
	}

	protected List<Card> getCharterFlightCards() {
		return player.getFilteredHand(this::isCardCurrentLocation);
	}

	protected boolean isCardCurrentLocation(Card card) {
		return card.getCity().filter(player.getLocation()::equals).isPresent();
	}

	protected void performCharterFlightAction(Card usingCard, City flyTo) {
		City city = usingCard.getCity().orElseThrow(RuntimeException::new);
		if (!city.equals(player.getLocation()))
			throw new RuntimeException("Invalid Card to charter flight");
		player.setLocation(flyTo);
		player.discardCard(usingCard);
	}

}
