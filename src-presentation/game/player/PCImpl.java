package game.player;

import java.util.List;

import data.GameColor;
import game.cards.Card;
import game.cards.CardCity;

public class PCImpl implements PlayerController {
	private Player player;

	@Override
	public void drive(CardCity card) {

	}

	@Override
	public void directFlight(CardCity card) {
		if (player.getDirectFlightCards().contains(card)) {
			player.removeCard(card);
			player.moveTo(card.getCity().orElseThrow(RuntimeException::new));
		} else
			throw new RuntimeException("Cannot direct flight with this card");

	}

	@Override
	public void charterFlight(CardCity card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shuttleFlight(CardCity card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void buildResearchStation(CardCity card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void treatDisease(GameColor diseaseColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void giveKnowledge(Player otherPlayer, CardCity card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void takeKnowledge(Player otherPlayer, CardCity card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void discoverCure(List<CardCity> cards) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useEventCard(Card card) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useSpecialSkill() {
		// TODO Auto-generated method stub

	}

}
