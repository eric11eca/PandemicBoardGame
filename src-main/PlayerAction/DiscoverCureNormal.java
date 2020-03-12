package PlayerAction;

import java.util.Set;

public class DiscoverCureNormal extends DiscoverCure {
	public DiscoverCureNormal(Set<String> cured) {
		curedDiseases = cured;
		cardCount = 5;
	}
}
