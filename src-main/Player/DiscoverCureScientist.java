package Player;

import java.util.Set;

public class DiscoverCureScientist extends DiscoverCure {

	public DiscoverCureScientist(Set<String> cured) {
		curedDiseases = cured;
		cardCount = 4;
	}
}
