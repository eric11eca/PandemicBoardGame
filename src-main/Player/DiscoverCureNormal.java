package Player;

import java.util.Map;
import java.util.Set;

import Card.PlayerCard;
import Initialize.Board;

public class DiscoverCureNormal extends DiscoverCure {

	public DiscoverCureNormal(Map<String, PlayerCard> cardHand, Set<String> cured) {
		hand = cardHand;
		curedDiseases = cured;
		cardCount = 5;
	}
}
