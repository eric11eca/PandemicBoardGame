package Vaccine;

/**
 * Vaccine that can help a city avoid one infection
 * from one single disease covered by this vaccine
 */
public class BasicVaccineSingle extends Vaccine{
	public BasicVaccineSingle() {
		description = "Basic Vaccine Single";
	}

	@Override
	public int cost() {
		return 50 + diseaseCostSum();
	}

}
