package game.city;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class CitySet {
	private Set<City> cities;

	public CitySet(Set<City> cities) {
		super();
		this.cities = cities;
	}

	public Set<City> getCitiesSatisfying(Predicate<? super City> predicate) {
		Set<City> set = new HashSet<>();
		cities.stream().filter(predicate).forEach(set::add);
		return set;
	}

	public Set<City> getAllCities() {
		return getCitiesSatisfying(c -> true);
	}
}
