package Player;

import java.util.Map;
import java.util.Set;

import Card.PlayerCard;

public class DiscoverCureScientist extends DiscoverCure {
	
	public DiscoverCureScientist(Map<String, PlayerCard> cardHand, Set<String> cured) {
		hand = cardHand;
	    curedDiseases = cured;
	    cardCount = 4;
	}
}
