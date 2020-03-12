package Vaccine;

import java.util.List;

public abstract class Vaccine {
	String description = "Unknown Vaccine";
	Disease disease;
	List<Disease> diseases;
	public String getDescription() { 
		return " " + description;
	}
	
	public abstract int cost();
	
	public void setSinglieVaccine(Disease disease) {
		this.disease = disease;
	}
	
	public void setMultipleDisease(Disease disease) {
		diseases.add(disease);
	}
	
	public int diseaseCostSum() {
		if(!disease.equals(null)) {
			return diseaseCost(disease);
		}
		
		int cost = 0;
		for(Disease disease: diseases) {
			cost += diseaseCost(disease);
		}
		return cost;
	}
	
	private int diseaseCost(Disease diseas) {
		int cost = 0;
		switch (disease) {
			case YELLOW: cost += 100;
			case RED: cost += 90;
			case BLACK: cost += 200;
			case BLUE: cost += 50; 
			default: cost += 0;
		}
		return cost;
	}
}
