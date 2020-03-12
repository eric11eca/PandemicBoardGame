package Vaccine;

/**
 * Vaccine that can help a city avoid one infection
 * from multiple diseases covered by this vaccine
 */
public class BasicVaccineMultiple extends Vaccine {
	public BasicVaccineMultiple() {
		description = "Basic Vaccine Multiple";
	}
	
	@Override
	public int cost() {
		return 100 + diseaseCostSum();
	}
}
