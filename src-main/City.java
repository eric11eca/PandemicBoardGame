import java.util.HashSet;

public class City {
	private int X;
	private int Y;
	
	private String name;
	private int diseaseCube;
	private String color;
	private Boolean researchStation;
	private HashSet<City> neighbors;

	public City() {
		this.diseaseCube = 0;
		this.researchStation = false;
		this.neighbors = new HashSet<City>();
	}
	
	public int getX() {
		return this.X;
	}
	
	public int getY() {
		return this.Y;
	}
	
	public void setX(int X) {
		this.X = X;
	}
	
	public void setY(int Y) {
		this.Y = Y;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setNeighbors(HashSet<City> neighbors){
		this.neighbors = neighbors;
	}
	
	public HashSet<City> getNeighbors() {
		return this.neighbors;
	}

	public Boolean getResearchStation() {
		return this.researchStation;
	}

	public void placeResearchStation() {
		this.researchStation = true;
	}

	public void placeDiseaseCube(int diseaseCube) {
		this.diseaseCube = diseaseCube;
	}

	public int getDiseaseCube() {
		return this.diseaseCube;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}
