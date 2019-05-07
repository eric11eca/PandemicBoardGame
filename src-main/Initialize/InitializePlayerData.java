package Initialize;

import java.util.Collections;
import java.util.Comparator;

import Card.PlayerCard;
import Player.Player;

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
			Player player = board.playerTable.get(role);
			board.currentPlayers.add(player);
		}
	}

	public void drawHandCard() {
		for(int i = 0; i < board.playernumber; i++){
			for(int j = 0; j < board.initialhandcard; j++){
				int topOfDeck = board.validPlayerCards.size() - 1;
				PlayerCard playercard = board.validPlayerCards.remove(topOfDeck);
				board.currentPlayers.get(i).playerData.hand.put(playercard.cardName, playercard); 
			}
		}	
	}

	public void sortPlayer() {
		PopulationComparator comparator = new PopulationComparator();
		Collections.sort(board.currentPlayers,comparator);
	}

	public int populationSum(Player player) {
		int totalPopulation = 0;
		for(String i: player.playerData.hand.keySet()){
			PlayerCard playercard = player.playerData.hand.get(i);
			if(playercard.cardType.equals(Board.CardType.CITYCARD)){
				totalPopulation += board.cities.get(playercard.cardName).population;
			}
		}
		return totalPopulation;
	}
	
	class PopulationComparator implements Comparator<Player>{

		@Override
		public int compare(Player player1, Player player2) {
			return populationSum(player2) - populationSum(player1);
		}
		
	}

}
