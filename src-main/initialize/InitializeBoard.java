package initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import buttonListeners.Messages;
import cardActions.EventCardAction;
import cards.Airlift;
import cards.ForecastEvent;
import cards.GovernmentGrant;
import cards.OneQuietNight;
import cards.PlayerCard;
import cards.ResilientPopulation;
import data.Board;
import data.City;
import parse.CityDataParser;
import player.DiscoverCureNormal;
import player.DiscoverCureScientist;
import player.Player;
import player.PlayerData;
import player.StationBuilderNormal;
import player.StationBuilderOperationsExpert;
import player.TreatMedic;
import player.TreatNormal;
import playerAction.ContingencyPlannerAction;
import playerAction.MedicAction;
import playerAction.OperationsExpertAction;

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
	
	public void initializeMessageBundle(String language, String region) {
		Locale defaultLocale = Locale.getDefault();
		
		if(language != null) {
			if(region != null) {
				board.messages = new Messages(new Locale(language, region));
			} else {
				board.messages = new Messages(new Locale(language));
			}
			
		} else  {
			board.messages = new Messages(defaultLocale);
		}
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

	public void initializePlayerRoles() {
		board.roles.add(Board.Roles.SCIENTIST);
		board.roles.add(Board.Roles.MEDIC);
		board.roles.add(Board.Roles.RESEARCHER);
		board.roles.add(Board.Roles.DISPATCHER);
		board.roles.add(Board.Roles.CONTINGENCYPLANNER);
		board.roles.add(Board.Roles.OPERATIONSEXPERT);
		board.roles.add(Board.Roles.QUARANTINESPECIALIST);
	}

	public void initializeSpecialEndGameDemo() {
		board.curedDiseases.add("YELLOW");
		board.curedDiseases.add("BLACK");
		board.curedDiseases.add("BLUE");

		List<PlayerCard> cardsTobeRemoved = new ArrayList<>();
		PlayerData playerData = board.currentPlayers.get(0).playerData;
		Set<String> handNames = playerData.hand.keySet();

		if (board.initialhandcard == 4) {
			int i = 0;
			for (String name : handNames) {
				if (i == 2) {
					break;
				}
				cardsTobeRemoved.add(board.currentPlayer.playerData.hand.get(name));
				i += 1;
			}
		} else if (board.initialhandcard == 3) {
			int i = 0;
			for (String name : handNames) {
				if (i == 1) {
					break;
				}
				cardsTobeRemoved.add(board.currentPlayer.playerData.hand.get(name));
				i += 1;
			}
		}

		for (PlayerCard card : cardsTobeRemoved) {
			playerData.hand.remove(card.cardName);
			board.validPlayerCards.remove(card);
		}

		for (PlayerCard playerCard : board.validPlayerCards) {
			if (playerCard.cardType.equals(Board.CardType.CITYCARD)) {
				if (playerCard.color.equals("RED")) {
					playerData.hand.put(playerCard.cardName, playerCard);
					cardsTobeRemoved.add(playerCard);
				}
				if (playerData.hand.size() == 7) {
					break;
				}
			}
		}

		for (PlayerCard card : cardsTobeRemoved) {
			board.validPlayerCards.remove(card);
		}
	}
	
	public void initializeEventCardAction() {
		Airlift airlift = new Airlift(board);
		board.eventCards.put("Airlift", airlift);
		ForecastEvent forcast = new ForecastEvent(board);
		board.eventCards.put("Forecast", forcast);
		OneQuietNight oneQuiteNight = new OneQuietNight(board);
		board.eventCards.put("OneQuietNight", oneQuiteNight);
		GovernmentGrant governmentGrant = new GovernmentGrant(board);
		board.eventCards.put("GovernmentGrant", governmentGrant);
		ResilientPopulation resilientPopulation = new ResilientPopulation(board);
		board.eventCards.put("ResilientPopulation", resilientPopulation);
		EventCardAction eventCardAction = new EventCardAction(board);
		board.eventCardAction = eventCardAction;
	}

	public void initializePlayerTable() {
		PlayerData scientistData = new PlayerData();
		PlayerData medicData = new PlayerData();
		PlayerData researcherData = new PlayerData();
		PlayerData dispatcherData = new PlayerData();
		PlayerData contingencyPlannerData = new PlayerData();
		PlayerData operationsExpertData = new PlayerData();
		PlayerData quarantineSpecialistData = new PlayerData();

		scientistData.role = Board.Roles.SCIENTIST;
		medicData.role = Board.Roles.MEDIC;
		researcherData.role = Board.Roles.RESEARCHER;
		dispatcherData.role = Board.Roles.DISPATCHER;
		contingencyPlannerData.role = Board.Roles.CONTINGENCYPLANNER;
		operationsExpertData.role = Board.Roles.OPERATIONSEXPERT;
		quarantineSpecialistData.role = Board.Roles.QUARANTINESPECIALIST;
		
		
		scientistData.buildStationModel = new StationBuilderNormal(scientistData, board);
		medicData.buildStationModel = new StationBuilderNormal(medicData, board);
		researcherData.buildStationModel = new StationBuilderNormal(researcherData, board);
		dispatcherData.buildStationModel = new StationBuilderNormal(dispatcherData, board);
		contingencyPlannerData.buildStationModel = new StationBuilderNormal(contingencyPlannerData, board);
		quarantineSpecialistData.buildStationModel = new StationBuilderNormal(quarantineSpecialistData, board);
		operationsExpertData.buildStationModel = new StationBuilderOperationsExpert(operationsExpertData, board);
		
		scientistData.discoverCure = new DiscoverCureScientist(board.curedDiseases);
		medicData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		researcherData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		dispatcherData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		operationsExpertData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		contingencyPlannerData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialistData.discoverCure = new DiscoverCureNormal(board.curedDiseases);
		
		scientistData.treatAction = new TreatNormal(scientistData, board);
		medicData.treatAction = new TreatMedic(medicData, board);
		researcherData.treatAction = new TreatNormal(researcherData, board);
		dispatcherData.treatAction = new TreatNormal(dispatcherData, board);
		operationsExpertData.treatAction = new TreatNormal(operationsExpertData, board);
		contingencyPlannerData.treatAction = new TreatNormal(contingencyPlannerData, board);
		quarantineSpecialistData.treatAction = new TreatNormal(quarantineSpecialistData, board);		
		
		operationsExpertData.specialSkill = new OperationsExpertAction(board, operationsExpertData);
		medicData.specialSkill = new MedicAction(board, medicData);
		contingencyPlannerData.specialSkill = new ContingencyPlannerAction(board, contingencyPlannerData);
		
		Player scientist = new Player(board, scientistData);
		Player medic = new Player(board, medicData);
		Player researcher = new Player(board, researcherData);
		Player dispatcher = new Player(board, dispatcherData);
		Player contingencyPlanner = new Player(board, contingencyPlannerData);
		Player operationsExpert = new Player(board, operationsExpertData);
		Player quarantineSpecialist = new Player(board, quarantineSpecialistData);
		
		scientist.eventCardAction = new EventCardAction(board);
		medic.eventCardAction = new EventCardAction(board);
		researcher.eventCardAction = new EventCardAction(board);
		dispatcher.eventCardAction = new EventCardAction(board);
		contingencyPlanner.eventCardAction = new EventCardAction(board);
		operationsExpert.eventCardAction = new EventCardAction(board);
		quarantineSpecialist.eventCardAction = new EventCardAction(board);

		board.playerTable.put(Board.Roles.SCIENTIST, scientist);
		board.playerTable.put(Board.Roles.MEDIC, medic);
		board.playerTable.put(Board.Roles.RESEARCHER, researcher);
		board.playerTable.put(Board.Roles.DISPATCHER, dispatcher);
		board.playerTable.put(Board.Roles.CONTINGENCYPLANNER, contingencyPlanner);
		board.playerTable.put(Board.Roles.OPERATIONSEXPERT, operationsExpert);
		board.playerTable.put(Board.Roles.QUARANTINESPECIALIST, quarantineSpecialist);
	}

	public void initializeInfectionRateTrack() {
		board.infectionRateTracker.push(4);
		board.infectionRateTracker.push(4);
		board.infectionRateTracker.push(3);
		board.infectionRateTracker.push(3);
		board.infectionRateTracker.push(2);
		board.infectionRateTracker.push(2);
		board.infectionRateTracker.push(2);
	}

	public void initializeRemainDiseaseCube() {
		board.remainDiseaseCube.put("RED", 24);
		board.remainDiseaseCube.put("BLUE", 24);
		board.remainDiseaseCube.put("BLACK", 24);
		board.remainDiseaseCube.put("YELLOW", 24);
	}

	public void initializeCity(City city) {
		city.diseaseCubes.put("RED", 0);
		city.diseaseCubes.put("BLUE", 0);
		city.diseaseCubes.put("BLACK", 0);
		city.diseaseCubes.put("YELLOW", 0);
		board.cities.put(city.cityName, city);
	}

	public void initializeInfectionCard(String cityName) {
		board.validInfectionCards.add(cityName);
	}

	public void initializeDiseaseCube() {
		for (int i = 0; i < 9; i++) {
			String cardName = board.validInfectionCards.get(0);
			City city = board.cities.get(cardName);
			if (i < 3) {
				placeDiseaseCube(city, 3);
			} else if (i > 2 && i < 6) {
				placeDiseaseCube(city, 2);
			} else {
				placeDiseaseCube(city, 1);
			}
			board.cities.replace(cardName, city);
			board.validInfectionCards.remove(0);
			board.discardInfectionCards.add(0, cardName);
		}
	}

	private void placeDiseaseCube(City city, int count) {
		int numOfCubes = board.remainDiseaseCube.get(city.color);
		city.diseaseCubes.put(city.color, count);
		board.remainDiseaseCube.put(city.color, numOfCubes - count);
	}

	public void shuffleCards() {
		Collections.shuffle(board.validInfectionCards);
		Collections.shuffle(board.validPlayerCards);
	}

	public void initializePlayerCard(Board.CardType cardType, String cardName) {
		PlayerCard cityCard = new PlayerCard(cardType, cardName);
		cityCard.color = board.cities.get(cardName).color;
		board.validPlayerCards.add(cityCard);
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
			board.validPlayerCards.add(randomIdx + count, new PlayerCard(cardType, ""));
			count += 1;
		}
	}

	public void initializeEventCard() {
		for (String cardName : eventCardNames) {
			board.validPlayerCards.add(new PlayerCard(Board.CardType.EVENTCARD, cardName));
		}
	}

}
