package parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import data.CityData;
import data.GameColor;
import data.GameProperty;
import game.city.City;
import game.disease.CubeData;
import render.RenderCity;

public abstract class CityLoader {
	private Map<String, City> allCities;
	private Map<City, RenderCity> cityToRenderCity;

	public CityLoader(Map<String, City> allCities, Map<City, RenderCity> cityToRenderCity) {
		this.allCities = allCities;
		this.cityToRenderCity = cityToRenderCity;
	}

	public void loadCities() throws IOException {
		HashMap<City, Set<String>> cityToNeighbors = new HashMap<>();
		HashMap<City, Set<City>> cityToNeighborSet = new HashMap<>();
		readCitiesFromFile(getDataFile(), cityToNeighbors, cityToNeighborSet);
		setNeighborPointers(cityToNeighbors, cityToNeighborSet);
	}

	private File getDataFile() {
		String dataFolder = GameProperty.getInstance().get("DATA_PATH");
		String cityData = GameProperty.getInstance().get("CITY_DATA");
		File dataFile = new File(dataFolder + cityData);
		return dataFile;
	}

	private GameColor parseColor(String color) {
		return GameColor.valueOf(color);
	}

	private void readCitiesFromFile(File file, HashMap<City, Set<String>> cityToNeighbors,
			HashMap<City, Set<City>> cityToNeighborSet) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String newline;
		while ((newline = br.readLine()) != null) {
			parseCity(newline.split(","), cityToNeighbors, cityToNeighborSet);
		}
		br.close();
	}

	private void parseCity(String[] raw, HashMap<City, Set<String>> cityToNeighbors,
			HashMap<City, Set<City>> cityToNeighborSet) {
		String cityName = raw[0];
		GameColor color = parseColor(raw[1]);
		int population = Integer.parseInt(raw[2]);
		int x = Integer.parseInt(raw[3]);
		int y = Integer.parseInt(raw[4]);
		boolean start = raw[5].equals("start");
		Set<String> neighbors = parseNeighborsFromRaw(raw);

		City city = createCity(cityName, color, population, start, cityToNeighborSet);
		cityToNeighbors.put(city, neighbors);
		RenderCity renderCity = new RenderCity(x, y, city);
		cityToRenderCity.put(city, renderCity);
	}

	private City createCity(String cityName, GameColor color, int population, boolean start,
			HashMap<City, Set<City>> cityToNeighborSet) {
		CityData cityData = new CityData(cityName, color, population, start);
		CubeData cubeData = createCubeData();
		Set<City> neighborSet = new HashSet<>();
		City city = new City(cityData, cubeData, neighborSet);
		cityToNeighborSet.put(city, neighborSet);
		allCities.put(cityName, city);
		return city;
	}

	private Set<String> parseNeighborsFromRaw(String[] raw) {
		Set<String> neighbors = new HashSet<>();
		for (int i = 6; i < raw.length; i++) {
			neighbors.add(raw[i]);
		}
		return neighbors;
	}

	private void setNeighborPointers(HashMap<City, Set<String>> cityToNeighbors,
			HashMap<City, Set<City>> cityToNeighborSet) {
		cityToNeighbors.forEach((city, neighbors) -> {
			Set<City> neighborSet = cityToNeighborSet.get(city);
			for (String n : neighbors) {
				neighborSet.add(allCities.get(n));
			}
		});
	}

	protected abstract CubeData createCubeData();

}
