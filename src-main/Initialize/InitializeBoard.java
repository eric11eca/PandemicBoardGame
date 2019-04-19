package Initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import Card.PlayerCard;
import Parse.CityDataParser;
import Player.ContingencyPlanner;
import Player.Dispatcher;
import Player.Medic;
import Player.OperationsExpert;
import Player.Player;
import Player.QuarantineSpecialist;
import Player.Scientist;

public class InitializeBoard {
	public Board board;
	public CityDataParser cityDataParser;
	public String cityDataPath = "CityData";
	public ThreadLocalRandom random;
	public ArrayList<String> eventCardNames;

	public InitializeBoard(Board mainBoard) {
		random = ThreadLocalRandom.current();
		this.board = mainBoard;
		this.cityDataParser = new CityDataParser();
		this.eventCardNames = new ArrayList<String>();
	}

	public void initializeWithCityData() {
		List<List<String>> citiesData = this.cityDataParser.parse(this.cityDataPath);
		for (List<String> cityData : citiesData) {
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
		initializeNeighbors(citiesData);
	}

	public void initializeNeighbors(List<List<String>> citiesData) {
		for (List<String> cityData : citiesData) {
			String cityName = cityData.get(0);
			City city = board.cities.get(cityName);
			for (int i = 5; i < cityData.size(); i++) {
				String neighborName = cityData.get(i);
				City neighbor = board.cities.get(neighborName);
				city.neighbors.put(neighborName, neighbor);
			}
		}
	}
	
	public void initializeRoleDeck() {
		board.roleCardDeck.add("Scientist");
		board.roleCardDeck.add("Medic");
		board.roleCardDeck.add("OperationsExpert");
		board.roleCardDeck.add("ContingencyPlanner");
		board.roleCardDeck.add("Dispatcher");
		board.roleCardDeck.add("QuarantineSpecialist");
	}
	
	public void initializeCurrentPlayers() {
		Collections.shuffle(board.roleCardDeck);
		for (int i = 0; i < board.playernumber; i++) {
			String roleName = board.roleCardDeck.get(i);
			Player player;
			switch(roleName) {
			case "Scientist":
				player = new Scientist(board);
				player.role = "Scientist";
				board.currentPlayers.add(player);
				break;
			case "Medic":
		    	player = new Medic(board);
		    	player.role = "Medic";
		    	board.currentPlayers.add(player);
		    	break;
			case "OperationsExpert":
				player = new OperationsExpert(board);
				player.role = "OperationsExpert";
				board.currentPlayers.add(player);
				break;
			case "ContingencyPlanner":
				player = new ContingencyPlanner(board);
				player.role = "ContingencyPlanner";
				board.currentPlayers.add(player);
				break;
			case "Dispatcher":
				player = new Dispatcher(board);
				player.role = "Dispatcher";
				board.currentPlayers.add(player);
				break;
			case "QuarantineSpecialist":
				player = new QuarantineSpecialist(board);
				player.role = "QuarantineSpecialist";
				board.currentPlayers.add(player);
				break;
			}
			
		}
	}
	
	public void initializeSpecialEndGameDemo() {
		board.curedDiseases.add("YELLOW");
		board.curedDiseases.add("BLACK");
		board.curedDiseases.add("BLUE");
		
		List<PlayerCard> cardsTobeRemoved= new ArrayList<>();
		Player player = board.currentPlayers.get(0);
		Set<String> handNames =  player.hand.keySet();
		
		if(board.initialhandcard == 4) {
			int i = 0;
			for (String name : handNames) {
				if(i == 2) {
					break;
				}
				cardsTobeRemoved.add(board.currentPlayer.hand.get(name));
				i += 2;
			}
		} else if (board.initialhandcard == 3) {
			int i = 0;
			for (String name : handNames) {
				if(i == 1) {
					break;
				}
				cardsTobeRemoved.add(board.currentPlayer.hand.get(name));
				i += 2;
			}
		}
		
		for(PlayerCard card : cardsTobeRemoved) {
			board.validPlayerCard.remove(card);
		}
		
		for(PlayerCard playerCard : board.validPlayerCard) {
			if(playerCard.cardType.equals(Board.CardType.CITYCARD)) {
				if(playerCard.color.equals("RED")) {
					player.hand.put(playerCard.cardName, playerCard);
					cardsTobeRemoved.add(playerCard);
					board.discardPlayerCard.put(playerCard.cardName, playerCard);
				}
				if(player.hand.size() == 7) {
					break;
				}
			}
		}
		
		for(PlayerCard card : cardsTobeRemoved) {
			board.validPlayerCard.remove(card);
		}
	}
	
	public void initializeInfectionRateTrack() {
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(4);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(3);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
		board.infectionRateTrack.push(2);
	}

	public void initializeRemainDiseaseCube() {
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.remainDiseaseCube.put("BLACK", 24);
		board.remainDiseaseCube.put("YELLOW", 24);
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
				placeDiseaseCube(city, 3);
			} else if (i > 2 && i < 6) {
				placeDiseaseCube(city, 2);
			} else {
				placeDiseaseCube(city, 1);
			}
			board.cities.replace(cardName, city);
			board.validInfectionCard.remove(0);
			board.discardInfectionCard.add(0, cardName);
		}
	}

	private void placeDiseaseCube(City city, int count) {
		int numOfCubes = board.remainDiseaseCube.get(city.color);
		city.diseaseCubes.put(city.color, count);
		board.remainDiseaseCube.put(city.color, numOfCubes - count);
	}

	public void shuffleCards() {
		Collections.shuffle(board.validInfectionCard);
		Collections.shuffle(board.validPlayerCard);
	}

	public void initializePlayerCard(Board.CardType cardType, String cardName) {
		PlayerCard cityCard = new PlayerCard(cardType, cardName);
		cityCard.color = board.cities.get(cardName).color;
		board.validPlayerCard.add(cityCard);
	}

	public void initializeEpidemicCard(int validPlayerNum) {
		int count = 0;
		int deckSize;
		int playerCardNum = validPlayerNum;
		int epidemicCardNum = board.epidemicCardNum;
		Board.CardType cardType = Board.CardType.EPIDEMIC;

		double result = (double) playerCardNum / (double) epidemicCardNum;

		if (result > (double) (playerCardNum / epidemicCardNum) + 0.5) {
			deckSize = (int) Math.ceil(result);
		} else {
			deckSize = playerCardNum / epidemicCardNum;
		}

		int cardRemain = playerCardNum - deckSize * (epidemicCardNum - 1);

		for (int i = 0; i < epidemicCardNum; i++) {
			int randomIdx;
			int low = i * deckSize;
			int high = (i + 1) * deckSize;

			if (i == epidemicCardNum - 1) {
				randomIdx = random.nextInt(low, low + cardRemain);
			} else {
				randomIdx = random.nextInt(low, high);
			}
			board.validPlayerCard.add(randomIdx + count, new PlayerCard(cardType, ""));
			count += 1;
		}
	}

	public void initializeEventCard() {
		for (String cardName : eventCardNames) {
			board.validPlayerCard.add(new PlayerCard(Board.CardType.EVENTCARD, cardName));
		}
	}

}
