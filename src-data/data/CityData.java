package data;

import java.util.Objects;
import java.util.Set;

public class CityData {
	private String cityName;
	private GameColor color;
	private int population;

	public CityData(String cityName, GameColor color, int population) {
		this.cityName = cityName;
		this.color = color;
		this.population = population;
	}

	public String getCityName() {
		return cityName;
	}

	public GameColor getColor() {
		return color;
	}

	public int getPopulation() {
		return population;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cityName, color, population);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityData other = (CityData) obj;
		if (!Objects.equals(cityName, other.cityName))
			return false;
		if (color != other.color)
			return false;
		if (population != other.population)
			return false;
		return true;
	}
}