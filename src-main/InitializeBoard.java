import java.util.List;

public class InitializeBoard {
	Board board;
	CityDataParser cityDataParser;
	String cityDataPath = "CityData";
	
	public InitializeBoard(Board mainBoard){
		this.board = mainBoard;
		this.cityDataParser = new CityDataParser();
	}
	
	public void initializeCity(){
		List<List<String>> citiesData = this.cityDataParser.parse(this.cityDataPath);
		for(List<String> cityData : citiesData) {
			City city = new City(cityData.get(0), cityData.get(1));
			board.cities.put(cityData.get(0), city);
		} 
	}
	
	public void initializeInfectionCard(String cityName) {
		board.valid_infection_card.add(cityName);
	}
}
