package parse;

import data.GameProperty;

public class InfectionRateLoader {
	public int[] getInfectionRates() {
		String[] rawRates = GameProperty.getInstance().get("INFECTION_RATES").split(",");
		int[] rates = new int[rawRates.length];
		for (int i = 0; i < rates.length; i++) {
			rates[i] = Integer.parseInt(rawRates[i]);
		}
		return rates;
	}
}
