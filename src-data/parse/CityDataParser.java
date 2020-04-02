package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import data.CityBuilder;
import data.CityData;
import data.GameColor;
import game.city.City;
import render.RenderCity;

public class CityDataParser {
	public HashMap<City, RenderCity> loadCities(File file) throws IOException {
		HashMap<City, RenderCity> map = new HashMap<>();
		HashMap<String, Set<String>> cityToNeighbors = new HashMap<>();
		HashMap<String, CityBuilder> cities = new HashMap<>();
		readCitiesFromFile(file, map, cityToNeighbors, cities);
		setNeighborPointers(cityToNeighbors, cities);
		return map;
	}

	private GameColor parseColor(String color) {
		return GameColor.valueOf(color);
	}

	private void readCitiesFromFile(File file, HashMap<City, RenderCity> map,
			HashMap<String, Set<String>> cityToNeighbors, HashMap<String, CityBuilder> cities) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String newline;
		while ((newline = br.readLine()) != null) {
			parseCity(newline.split(","), map, cityToNeighbors, cities);
		}
		br.close();
	}

	private void parseCity(String[] raw, HashMap<City, RenderCity> map, HashMap<String, Set<String>> cityToNeighbors,
			HashMap<String, CityBuilder> cities) {
		String cityName = raw[0];
		GameColor color = parseColor(raw[1]);
		int population = Integer.parseInt(raw[2]);
		int x = Integer.parseInt(raw[3]);
		int y = Integer.parseInt(raw[4]);
		boolean start = raw[5].equals("start");
		Set<String> neighbors = parseNeighborsFromRaw(raw);

		CityBuilder builder = createCityBuilder(cityName, color, population, start);
		cities.put(cityName, builder);
		cityToNeighbors.put(cityName, neighbors);

		RenderCity renderCity = new RenderCity(x, y, builder.getCity());
		map.put(builder.getCity(), renderCity);
	}

	private CityBuilder createCityBuilder(String cityName, GameColor color, int population, boolean start) {
		CityData data = new CityData(cityName, color, population, start);
		CityBuilder builder = new CityBuilder();
		builder.buildFromCityData(data);
		return builder;
	}

	private Set<String> parseNeighborsFromRaw(String[] raw) {
		Set<String> neighbors = new HashSet<>();
		for (int i = 6; i < raw.length; i++) {
			neighbors.add(raw[i]);
		}
		return neighbors;
	}

	private void setNeighborPointers(HashMap<String, Set<String>> cityToNeighbors,
			HashMap<String, CityBuilder> cities) {
		cityToNeighbors.forEach((cityName, neighbors) -> {
			CityBuilder cityBuilder = cities.get(cityName);
			for (String n : neighbors) {
				cityBuilder.addNeighbor(cities.get(n).getCity());
			}
		});
	}

}
