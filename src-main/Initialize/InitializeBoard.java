package Initialize;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Card.PlayerCard;
import Parse.CityDataParser;

public class InitializeBoard {
	public Board board;
	public CityDataParser cityDataParser;
	public String cityDataPath = "CityData";
	public ThreadLocalRandom random;
	public ArrayList<String> eventCardNames;
	
	public InitializeBoard(Board mainBoard){
		random =  ThreadLocalRandom.current();
		this.board = mainBoard;
		this.cityDataParser = new CityDataParser();
		this.eventCardNames = new ArrayList<String>();
	}
	
	public void initializeWithCityData(){
		List<List<String>> citiesData = this.cityDataParser.parse(this.cityDataPath);
		for(List<String> cityData : citiesData) {
			String cityName = cityData.get(0);
			String color = cityData.get(1);
			Integer population = Integer.parseInt(cityData.get(2));
			Integer x = Integer.parseInt(cityData.get(3));
			Integer y = Integer.parseInt(cityData.get(4));
			
			City city = new City(cityName, color, population, x, y);
			initializeCity(city);
			initializeInfectionCard(cityName);
			initializePlayerCard(Board.CardType.CITYCARD, cityName);
		} 
	}

	public void initializeCity(City city) {
		board.cities.put(city.cityName, city);
	}
	
	public void initializeInfectionCard(String cityName) {
		board.validInfectionCard.add(cityName);
	}
	
	public void initializeDiseaseCube() {
		for (int i = 0; i < 9; i++) {
			String cardName = board.validInfectionCard.get(0);
			City city = board.cities.get(cardName);
			
			if (i < 3) {
				city.diseaseCubes.put(city.color, 3);
			} else if (i > 2 && i < 6) {
				city.diseaseCubes.put(city.color, 2);
			} else {
				city.diseaseCubes.put(city.color, 1);
			}
			board.cities.replace(cardName, city);
			board.validInfectionCard.remove(0);
			board.discardInfectionCard.add(0, cardName);
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(board.validInfectionCard);
		Collections.shuffle(board.validPlayerCard);
	}
	
	public void initializePlayerCard(Board.CardType cardType, String cardName) {
		board.validPlayerCard.add(new PlayerCard(cardType, cardName));
	}	
	
	public void initializeEpidemicCard(int validPlayerNum) {
		int count = 0;
		int deckSize;
		int playerCardNum = validPlayerNum;
		int epidemicCardNum = board.epidemicCardNum;
		Board.CardType cardType = Board.CardType.EPIDEMIC;
		
		double result = (double)playerCardNum / (double)epidemicCardNum;
		
		if (result > (double)(playerCardNum / epidemicCardNum) + 0.5) {
			deckSize = (int)Math.ceil(result);
		} else {
			deckSize = playerCardNum / epidemicCardNum;
		}
		
		int cardRemain = playerCardNum - deckSize * (epidemicCardNum - 1);
		
		for (int i = 0; i < epidemicCardNum ; i++) {
			int randomIdx;
			int low = i*deckSize;
			int high = (i+1)*deckSize;
			
			if(i == epidemicCardNum - 1) {
				randomIdx = random.nextInt(low, low + cardRemain);
			} else {
				randomIdx = random.nextInt(low, high);
			}
			board.validPlayerCard.add(randomIdx + count, new PlayerCard(cardType,""));
			count += 1;
		}
	}
	
	public void initializeEventCard() {
		for(String cardName : eventCardNames) {
			board.validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, cardName));
		}
	}

}
