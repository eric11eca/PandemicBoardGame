package initialize;

import java.util.Collections;
import java.util.Comparator;

import cards.PlayerCard;
import data.Board;
import game.player.Player;
import game.player.PlayerImpl;

public class InitializePlayerData {
	Board board;
	String prefix = "Player.";

	public InitializePlayerData(Board board) {
		this.board = board;
	}

	public void createPlayers() {
		Collections.shuffle(board.roles);

		for (int i = 0; i < board.playernumber; i++) {
			Board.Roles role = board.roles.get(i);
			PlayerImpl player = board.playerTable.get(role);
			board.currentPlayers.add(player);
		}
	}

	public void drawHandCard() {
		for (int i = 0; i < board.playernumber; i++) {
			for (int j = 0; j < board.initialhandcard; j++) {
				int topOfDeck = board.validPlayerCards.size() - 1;
				PlayerCard playercard = board.validPlayerCards.remove(topOfDeck);
				board.currentPlayers.get(i).playerData.hand.put(playercard.cardName, playercard);
			}
		}
	}

	public void sortPlayer() {
		PopulationComparator comparator = new PopulationComparator();
		Collections.sort(board.currentPlayers, comparator);
	}

	public int populationSum(Player player) {
		int totalPopulation = 0;
		for (String i : player.playerData.hand.keySet()) {
			PlayerCard playercard = player.playerData.hand.get(i);
			if (playercard.cardType.equals(Board.CardType.CITYCARD)) {
				totalPopulation += board.cities.get(playercard.cardName).getPopulation();
			}
		}
		return totalPopulation;
	}

	class PopulationComparator implements Comparator<PlayerImpl> {

		@Override
		public int compare(PlayerImpl player1, PlayerImpl player2) {
			return populationSum(player2) - populationSum(player1);
		}

	}

}
