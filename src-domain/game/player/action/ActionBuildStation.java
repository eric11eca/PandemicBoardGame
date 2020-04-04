package game.player.action;

import java.util.List;

import game.cards.Card;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public class ActionBuildStation extends PlayerAction {

	public ActionBuildStation(Player player, PlayerInteraction interaction) {
		super(player, interaction);
	}

	@Override
	public void perform() {
		interaction.selectOneCardFrom(getBuildResearchStationCards(), this::performBuildStationActionWithCard);
	}

	@Override
	public boolean canPerform() {
		boolean stationNotBuiltYet = !player.getLocation().hasResearchStation();
		boolean hasCardToBuild = !getBuildResearchStationCards().isEmpty();
		return stationNotBuiltYet && hasCardToBuild;
	}

	protected List<Card> getBuildResearchStationCards() {
		return player.getFilteredHand(this::canBuildResearchStationUsingCard);
	}

	protected boolean canBuildResearchStationUsingCard(Card card) {
		boolean cardIsCurrentCity = card.getCity().filter(player.getLocation()::equals).isPresent();
		return cardIsCurrentCity;
	}

	protected void performBuildStationActionWithCard(Card card) {
		City city = card.getCity().orElseThrow(RuntimeException::new);
		if (!city.equals(player.getLocation()))
			throw new RuntimeException("City card does not match current location");
		player.discardCard(card);
		player.getLocation().buildResearchStation();
	}
}
