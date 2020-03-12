package Vaccine;

public class OutbreakResistance extends VaccineDecorator{
	public OutbreakResistance(Vaccine vaccine) {
		this.vaccine = vaccine;
	}

	@Override
	public String getDescription() {
		return vaccine.getDescription() + ", OutbreakResistance";
	}

	@Override
	public int cost() {
		return vaccine.cost() + 180;
	}
}
