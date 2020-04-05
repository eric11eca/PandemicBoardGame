package game.player.action;

import java.util.List;
import java.util.Set;

import data.GameProperty;
import game.cards.Card;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionBuildStation extends Action {
	private boolean needCard;
	private CitySet citySet;

	public ActionBuildStation(Player player, PlayerInteraction interaction, boolean needCard, CitySet citySet) {
		super(player, interaction);
		this.needCard = needCard;
		this.citySet = citySet;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectOneCardFrom(getBuildResearchStationCards(),
				card -> this.afterSelectingCard(card, completionCallback));
	}

	@Override
	public boolean canPerform() {
		boolean stationNotBuiltYet = !playerCurrentLocation().hasResearchStation();
		boolean hasCardToBuild = !getBuildResearchStationCards().isEmpty();
		return stationNotBuiltYet && hasCardToBuild;
	}

	protected List<Card> getBuildResearchStationCards() {
		return player().getFilteredHand(this::canBuildResearchStationUsingCard);
	}

	protected boolean canBuildResearchStationUsingCard(Card card) {
		boolean cardIsCurrentCity = card.getCity().filter(playerCurrentLocation()::equals).isPresent();
		return cardIsCurrentCity;
	}

	protected void afterSelectingCard(Card selectedCard, Runnable completionCallback) {
		final int MAX_STATION_COUNT = GameProperty.getInstance().getInt("MAX_STATION_COUNT");
		if (getStationCount() > MAX_STATION_COUNT) {
			interaction.selectCityFrom(getCitiesWithStation(), city -> {
				city.removeResearchStation();
				this.performBuildStationActionWithCard(selectedCard, completionCallback);
			});
		} else {
			this.performBuildStationActionWithCard(selectedCard, completionCallback);
		}
	}

	protected int getStationCount() {
		return getCitiesWithStation().size();
	}

	protected Set<City> getCitiesWithStation() {
		return citySet.getCitiesSatisfying(c -> c.hasResearchStation());
	}

	protected void performBuildStationActionWithCard(Card card, Runnable completionCallback) {
		legalityCheckAndDiscard(card);
		this.performBuildStationAction(completionCallback);
	}

	private void legalityCheckAndDiscard(Card card) {
		if (!needCard && card == null)
			return;
		City city = card.getCity().orElseThrow(RuntimeException::new);
		if (!city.equals(playerCurrentLocation()))
			throw new RuntimeException("City card does not match current location");
		player().discardCard(card);
	}

	protected void performBuildStationAction(Runnable completionCallback) {
		playerCurrentLocation().buildResearchStation();
		completionCallback.run();
	}
}
