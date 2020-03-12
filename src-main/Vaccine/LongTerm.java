package Vaccine;

public class LongTerm extends VaccineDecorator{
	public LongTerm(Vaccine vaccine) {
		this.vaccine = vaccine;
	}
	
	@Override
	public String getDescription() {
		return vaccine.getDescription() + ", LongTerm";
	}
	
	@Override
	public int cost() {
		return vaccine.cost() + 200;
	}
	
}
