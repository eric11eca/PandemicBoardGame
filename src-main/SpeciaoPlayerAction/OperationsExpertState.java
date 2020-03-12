package SpeciaoPlayerAction;

import java.util.Map;

import cards.PlayerCard;
import data.Board;
import player.PlayerData;


public class OperationsExpertState implements State {
	public String cityName;
	private Board board;
	private PlayerData operationsExpert;
	
	public OperationsExpertState(PlayerData currentPlayerData) {
		board = Board.getInstance();
		operationsExpert = currentPlayerData;
	}

	public void moveToAnotherCity() {
		Map<String, PlayerCard> playerHand = operationsExpert.hand;
		for (String cardName : playerHand.keySet()) {
			if (cardName.equals(cityName)) {
				operationsExpert.location = board.cities.get(cityName);
				playerHand.remove(cardName);
			}
		}
	}

	@Override
	public void useSpecialSkill() {	
		moveToAnotherCity();
	}
}
