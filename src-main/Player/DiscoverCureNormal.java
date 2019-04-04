package Player;


import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Initialize.Board;

public class DiscoverCureNormal implements DiscoverCure{
	private Map<String, PlayerCard> hand;
	private Set<String> curedDiseases;
	private int red_count = 0;
	private int blue_count = 0;
	private int black_count = 0;
	private int yellow_count = 0;
	
	public DiscoverCureNormal(Map<String, PlayerCard> hand, Set<String> curedDiseases) {
		this.hand = hand;
		this.curedDiseases = curedDiseases;
	}
	
	@Override
	public void discoverCure() {
		countColors();
		if(red_count >= 5) {
			curedDiseases.add("Red");
		} else if (blue_count >= 5) {
			curedDiseases.add("Blue");
		} else if (black_count >= 5) {
			curedDiseases.add("Black");
		} else if (yellow_count >= 5) {
			curedDiseases.add("Yellow");
		}
	}
	
	private void countColors() {
		for(PlayerCard card : hand.values()) {
			if(card.cardType == Board.CardType.CITYCARD) {
				if(card.color == "Yellow") {
					yellow_count++;
				} else if (card.color == "Red") {
					red_count++;
				} else if (card.color == "Blue") {
					blue_count++;
				} else if (card.color == "Black") {
					black_count++;
				}
			}
		}
	}

}
