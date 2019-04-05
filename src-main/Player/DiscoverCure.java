package Player;

import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Initialize.Board;

public abstract class DiscoverCure {
	public Map<String, PlayerCard> hand;
	public Set<String> curedDiseases;
	public int cardCount;
	private int red_count = 0;
	private int blue_count = 0;
	private int black_count = 0;
	private int yellow_count = 0;
	
	public void discoverCure() {
		countColors();
		if (red_count >= cardCount) {
			curedDiseases.add("RED");
		} else if (blue_count >= cardCount) {
			curedDiseases.add("BLUE");
		} else if (black_count >= cardCount) {
			curedDiseases.add("BLACK");
		} else if (yellow_count >= cardCount) {
			curedDiseases.add("YELLOW");
		}
	}
	
	private void countColors() {
		for (PlayerCard card : hand.values()) {
			if (card.cardType.equals(Board.CardType.CITYCARD)) {
				if (card.color.equals("YELLOW")) {
					yellow_count++;
				} else if (card.color.equals("RED")) {
					red_count++;
				} else if (card.color.equals("BLUE")) {
					blue_count++;
				} else if (card.color.equals("BLACK")) {
					black_count++;
				}
			}
		}
	}
}
