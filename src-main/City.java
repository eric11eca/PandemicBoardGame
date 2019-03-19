import java.util.ArrayList;

public class City {
	private int X;
	private int Y;
	
	private String name;
	
	private ArrayList<City> connections;
	private Boolean researchStation;
	private int diseaseCube;
	private String color;

	public City() {
		this.researchStation = false;
		this.connections = new ArrayList<City>();
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
	
	public void addConnections(City city){
		this.connections.add(city);
	}
	
	public ArrayList<City> getConnections() {
		return this.connections;
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
