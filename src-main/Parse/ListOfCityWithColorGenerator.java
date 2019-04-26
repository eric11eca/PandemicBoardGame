package Parse;

import java.util.Map;

import Initialize.City;

public class ListOfCityWithColorGenerator {
	public String[] concatColor(String[] citynames, Map<String, City> cities) {
		String[] cityWithColor = new String[citynames.length];
		for(int i = 0; i < citynames.length; i++) {
			try {
				String color = cities.get(citynames[i]).color;
				StringBuilder concatColorWithCity = new StringBuilder();
				concatColorWithCity.append("[");
				concatColorWithCity.append(color);
				concatColorWithCity.append("]");
				concatColorWithCity.append(" ");
				concatColorWithCity.append(citynames[i]);
				cityWithColor[i] = concatColorWithCity.toString();
			}catch(NullPointerException e) {
				cityWithColor[i] = citynames[i];
			}
		}
		return cityWithColor;
	}
}
