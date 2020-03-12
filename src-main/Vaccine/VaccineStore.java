package Vaccine;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import data.Board;

public class VaccineStore {
	private Board board;
	private Map<String, Vaccine> storage;
	private int vaccineCount;
	
	public VaccineStore() {
		this.board = Board.getInstance();
		this.storage = new HashMap<>();
		this.vaccineCount = 0;
	}
	
	public Vaccine getVaccine(String name) {
		return this.storage.get(name);
	}
	
	public void addVacine(String name, Vaccine vaccine) {
		this.storage.put(name, vaccine);
	}
	
	public void removeVaccine(String name) {
		this.storage.remove(name);
	}
	
	public int getCount() {
		return this.vaccineCount;
	}
	
	public Iterator<String> iterator() {
		return this.storage.keySet().iterator();
	}
	
	public void injectVaccine() {
	}
}
