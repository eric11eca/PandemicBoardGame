package Player;

import java.util.Map;
import java.util.Set;

import Card.PlayerCard;

public class DiscoverCureScientist implements DiscoverCure {
	private Map<String, PlayerCard> hand;
	private String disease;
	private Set<String> curedDiseases;
	
	public DiscoverCureScientist(Map<String, PlayerCard> hand, String disease, Set<String> curedDiseases) {
		this.hand = hand;
		this.disease = disease;
		this.curedDiseases = curedDiseases;
	}

	@Override
	public void discoverCure() {
		int count = 0;
		for(String cardName : hand.keySet()) {
			if(hand.get(cardName).color == disease) {
				count++;
			}
		}
		if(count >= 4) {
			curedDiseases.add(disease);
		}
	}
}
