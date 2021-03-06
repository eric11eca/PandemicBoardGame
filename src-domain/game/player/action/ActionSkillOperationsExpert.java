package game.player.action;

import java.util.List;

import game.cards.Card;
import game.city.City;
import game.city.CitySet;
import game.player.Player;
import game.player.PlayerInteraction;
import lang.I18n;

public class ActionSkillOperationsExpert extends Action {
	private CitySet citySet;

	public ActionSkillOperationsExpert(Player player, PlayerInteraction interaction, CitySet citySet) {
		super(player, interaction);
		this.citySet = citySet;
	}

	@Override
	public boolean isOncePerTurn() {
		return true;
	}

	@Override
	public void perform(Runnable completionCallback) {
		interaction.selectOneCardFrom(this.getCityCards(), I18n.format("action.skill.operation_expert.select_card"),
				card -> {
					interaction.selectCityFrom(citySet.getCitiesSatisfying(c -> !c.equals(playerCurrentLocation())),
							"action.skill.operation_expert.select_destination", city -> {
								this.performSpecialSkill(card, city, completionCallback);
							});
				});
	}

	@Override
	public boolean canPerform() {
		boolean isAtResearchStation = playerCurrentLocation().hasResearchStation();
		boolean hasCityCard = !getCityCards().isEmpty();
		return isAtResearchStation && hasCityCard;
	}

	protected List<Card> getCityCards() {
		return player().getFilteredHand(card -> card.getCity().isPresent());
	}

	protected void performSpecialSkill(Card toDiscard, City toMove, Runnable completionCallback) {
		player().setLocation(toMove);
		player().discardCard(toDiscard);
		completionCallback.run();
	}

}
