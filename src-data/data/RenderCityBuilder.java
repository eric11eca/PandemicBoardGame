package data;

import game.city.City;
import render.RenderCity;

public class RenderCityBuilder {
	private int x;
	private int y;
	private City city;

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setCity(City c) {
		city = c;
	}

	public RenderCity build() {
		return new RenderCity(x, y, city);
	}
}
