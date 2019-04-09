package Player;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Initialize.Board;

public class DiscoverCure {
	public Map<String, PlayerCard> hand;
	public Set<String> curedDiseases;
	public int cardCount;

	public boolean discoverCure(ArrayList<PlayerCard> cards) {
		if (cards.size() != cardCount) {
			throw new RuntimeException("Please select valid number of cards");
		}
		if (!validCardType(cards)) {
			throw new RuntimeException("City card only");
		}
		if (!checkCureColor(cards)) {
			throw new RuntimeException("Invalid City Card");
		}
		String curedColor = cards.get(0).color;
		return curedDiseases.add(curedColor);
	}

	public boolean validCardType(ArrayList<PlayerCard> cards) {
		Board.CardType cityCardType = Board.CardType.CITYCARD;
		for (PlayerCard playerCard : cards) {
			if (playerCard.cardType != cityCardType) {
				return false;
			}
		}
		return true;
	}

	public boolean checkCureColor(ArrayList<PlayerCard> cards) {
		String color = cards.get(0).color;
		for (int i = 1; i < cards.size(); i++) {
			if (!color.equals(cards.get(i).color)) {
				return false;
			}
		}
		return true;
	}
}
