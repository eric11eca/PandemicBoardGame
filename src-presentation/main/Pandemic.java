package main;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import game.GameSetup;
import game.city.City;
import gui.GameGUI;
import parse.CityDataParser;
import render.RenderCity;

public class Pandemic {

	public static void main(String[] args) throws IOException {
		CityDataParser cityDataParser = new CityDataParser();
		Map<City, RenderCity> cities = cityDataParser.loadCities(new File("CityData"));
		Set<City> citySet = new HashSet<>();
		citySet.addAll(cities.keySet());

		GameSetup gameSetup = new GameSetup(2, 4, citySet);
		gameSetup.setup();

		GameGUI gui = new GameGUI(cities);
		gui.showGUI();
	}

}
