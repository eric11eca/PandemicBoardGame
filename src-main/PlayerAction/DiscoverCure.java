package PlayerAction;

import java.util.List;
import java.util.Set;

import cards.PlayerCard;
import data.Board;

public abstract class DiscoverCure {
	public Set<String> curedDiseases;
	public int cardCount;

	public boolean discover(List<PlayerCard> cardsToCureDisease) {
		if (cardsToCureDisease.size() != cardCount) {
			throw new RuntimeException("IncorrectNumberOfCardsException");
		}
		if (!validCardType(cardsToCureDisease)) {
			throw new RuntimeException("CityCardException");
		}
		if (!checkCureColor(cardsToCureDisease)) {
			throw new RuntimeException("CityColorException");
		}
		String curedColor = cardsToCureDisease.get(0).color;
		return curedDiseases.add(curedColor);
	}

	public boolean validCardType(List<PlayerCard> cardsToCureDisease) {
		Board.CardType cityCardType = Board.CardType.CITYCARD;
		for (PlayerCard playerCard : cardsToCureDisease) {
			if (playerCard.cardType != cityCardType) {
				return false;
			}
		}
		return true;
	}

	public boolean checkCureColor(List<PlayerCard> cardsToCureDisease) {
		String color = cardsToCureDisease.get(0).color;
		for (int i = 1; i < cardsToCureDisease.size(); i++) {
			if (!color.equals(cardsToCureDisease.get(i).color)) {
				return false;
			}
		}
		return true;
	}
}
