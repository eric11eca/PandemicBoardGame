package parse;

import java.util.Map;

import initialize.City;

public class ListOfCityWithColorGenerator {
	public String[] concatColor(String[] citynames, Map<String, City> cities) {
		String[] cityWithColor = new String[citynames.length];
		for(int i = 0; i < citynames.length; i++) {
			try {
				String color = cities.get(citynames[i]).color;
				StringBuilder concatColorWithCity = new StringBuilder();
				concatColorWithCity.append(citynames[i]);
				concatColorWithCity.append(" ");
				concatColorWithCity.append("[");
				concatColorWithCity.append(color);
				concatColorWithCity.append("]");
				cityWithColor[i] = concatColorWithCity.toString();
			}catch(NullPointerException e) {
				cityWithColor[i] = citynames[i];
			}
		}
		return cityWithColor;
	}
}
