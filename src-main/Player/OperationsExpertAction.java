package Player;

import java.util.Map;

import Card.PlayerCard;
import Initialize.Board;

public class OperationsExpertAction implements SpecialSkill {
	public String cityName;
	private Board board;
	private PlayerData operationsExpert;
	
	public OperationsExpertAction(Board gameBoard, PlayerData currentPlayer) {
		board = gameBoard;
		operationsExpert = currentPlayer;
		currentPlayer.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		operationsExpert.buildStationModel = new StationBuilderOperationsExpert(operationsExpert, board);
	}

	public void moveToAnotherCity() {
		Map<String, PlayerCard> playerHand = operationsExpert.hand;
		for (String cardName : playerHand.keySet()) {
			if (cardName.equals(cityName)) {
				operationsExpert.location = board.cities.get(cityName);
				board.discardPlayerCard.put(cardName, playerHand.get(cardName));
				playerHand.remove(cardName);
			}
		}
	}

	@Override
	public void specialSkill() {	
		moveToAnotherCity();
	}
}
