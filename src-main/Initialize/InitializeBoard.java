package Initialize;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Card.PlayerCard;
import Parse.CityDataParser;

public class InitializeBoard {
	public Board board;
	public CityDataParser cityDataParser;
	public String cityDataPath = "CityData";
	public ThreadLocalRandom random;
	
	public InitializeBoard(Board mainBoard){
		random =  ThreadLocalRandom.current();
		this.board = mainBoard;
		this.cityDataParser = new CityDataParser();
	}
	
	public void initializeWithCityData(){
		List<List<String>> citiesData = this.cityDataParser.parse(this.cityDataPath);
		for(List<String> cityData : citiesData) {
			String cityName = cityData.get(0);
			initializeCity(cityName, cityData.get(1),
					Integer.parseInt(cityData.get(2)),
					Integer.parseInt(cityData.get(3)),
					Integer.parseInt(cityData.get(4)));
			initializeInfectionCard(cityName);
			initializePlayerCard(Board.CardType.CITYCARD, cityName);
		} 
	}

	public void initializeCity(String cityName, String color, int population, int x, int y) {
		City city = new City(cityName, color, population, x, y);
		board.cities.put(cityName, city);
	}
	
	public void initializeInfectionCard(String cityName) {
		board.validInfectionCard.add(cityName);
	}
	
	public void initializePlayerCard(Board.CardType cardType, String cardName) {
		board.validPlayerCard.add(new PlayerCard(cardType, cardName));
	}	
	
	public void initializeEpidemicCard(int playerCardNum, int epidemicCardNum) {
		int count = 0;
		Board.CardType cardType = Board.CardType.EPIDEMIC;
		
		double range = Math.ceil(playerCardNum / epidemicCardNum);
		int partitionSize = (int)range;
		
		for (int i = 0; i < range ; i++) {
			int randomNum = random.nextInt(i*(int)range, (i+1)*(int)range);
			board.validPlayerCard.add(randomNum, new PlayerCard(cardType,""));
		}
		

		
		
	}
}
