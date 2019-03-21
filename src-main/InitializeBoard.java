import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class InitializeBoard {
	Board board;
	CityDataParser cityDataParser;
	String cityDataPath = "CityData";
	ThreadLocalRandom random;
	
	public InitializeBoard(Board mainBoard){
		random =  ThreadLocalRandom.current();
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
	
	public void initializeEpidemicCard(int playerCardNum, int epidemicCardNum) {
		int count = 0;
		Board.CardType cardType = Board.CardType.EPIDEMIC;
		
		int range = (int) Math.ceil(playerCardNum / epidemicCardNum);
		
		
		if (playerCardNum == 44 && epidemicCardNum == 4) {
			for (int i = 0; i < range ; i++) {
				int randomNum = random.nextInt(i*range, (i+1)*range);
				board.valid_playerCard.add(randomNum, new PlayerCard(cardType,""));
			}
		} else if(playerCardNum == 44 && epidemicCardNum == 5) {
			
		}
		

		
		
	}
}
