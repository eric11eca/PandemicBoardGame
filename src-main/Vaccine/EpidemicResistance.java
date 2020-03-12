package Vaccine;

public class EpidemicResistance extends VaccineDecorator{
	public EpidemicResistance(Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	@Override
	public String getDescription() {
		return vaccine.getDescription() + ", EpidemicResistance";
	}

	@Override
	public int cost() {
		return vaccine.cost() + 120;
	}
}
