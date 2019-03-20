import java.util.List;

public class InitializeBoard {
	Board board;
	CityDataParser cityDataParser;
	String cityDataPath = "CityData";
	
	public InitializeBoard(Board mainBoard){
		this.board = mainBoard;
		this.cityDataParser = new CityDataParser();
	}
	
	public void initializeWithCityData(){
		List<List<String>> citiesData = this.cityDataParser.parse(this.cityDataPath);
		for(List<String> cityData : citiesData) {
			String cityName = cityData.get(0);
			initializeCity(cityName, cityData.get(1));
			initializeInfectionCard(cityName);
			initializePlayerCard(Board.CardType.CITYCARD, cityName);
		} 
	}
	
	public void initializeCity(String cityName, String color) {
		City city = new City(cityName, color);
		board.cities.put(cityName, city);
	}
	
	public void initializeInfectionCard(String cityName) {
		board.valid_infection_card.add(cityName);
	}
	
	public void initializePlayerCard(Board.CardType cardType, String cardName) {
		board.valid_playerCard.add(new PlayerCard(cardType, cardName));
	}	
}
