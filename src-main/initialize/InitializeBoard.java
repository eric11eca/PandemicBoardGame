package initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import PlayerAction.DiscoverCureNormal;
import PlayerAction.DiscoverCureScientist;
import PlayerAction.StationBuilderNormal;
import PlayerAction.StationBuilderOperationsExpert;
import PlayerAction.TreatMedic;
import PlayerAction.TreatNormal;
import SpeciaoPlayerAction.ContingencyPlannerState;
import SpeciaoPlayerAction.DispatcherState;
import SpeciaoPlayerAction.MedicState;
import SpeciaoPlayerAction.OperationsExpertState;
import cardActions.EpidemicCardAction;
import cardActions.EventCardAction;
import cardActions.InfectionCardAction;
import cards.EventCard;
import cards.EventCardFactory;
import cards.ForecastEvent;
import cards.GovernmentGrant;
import cards.OneQuietNight;
import cards.PlayerCard;
import cards.ResilientPopulation;
import data.Board;
import data.Card;
import data.CardDecks;
import data.City;
import data.Deck;
import data.DeckComponent;
import gameAction.ActionCommand;
import gameAction.BuildResearchStationCommand;
import gameAction.CharterFlightCommand;
import gameAction.CureDiseaseCommand;
import gameAction.DirectFlightCommand;
import gameAction.DrawTwoPlayerCardsCommand;
import gameAction.DriveCommand;
import gameAction.GameAction;
import gameAction.InfectionCommand;
import gameAction.PlayEventCardCommand;
import gameAction.ShareKnowledgeCommand;
import gameAction.ShuttleFlightCommand;
import gameAction.TreatDiseaseCommand;
import parse.CityDataParser;
import player.Player;
import player.PlayerData;

public class InitializeBoard {
	public Board board;
	public GameAction gameAction;
	public CityDataParser cityDataParser;
	public String cityDataPath = "CityData";
	public ThreadLocalRandom random;
	public ArrayList<String> eventCardNames;

	public InitializeBoard() {
		this.board = Board.getInstance();
		this.cityDataParser = new CityDataParser();
		this.eventCardNames = new ArrayList<String>();
		random = ThreadLocalRandom.current();
	}
	
	public void initializeWithCityData() {
		DeckComponent cardDecks = new CardDecks();
		DeckComponent playerCardDeck = new Deck();
		DeckComponent infectionCardDeck = new Deck();
		
		Board.CardType eventCardType = Board.CardType.EVENTCARD;
		Board.CardType cityCardType = Board.CardType.CITYCARD;
		
		List<List<String>> citiesData = cityDataParser.parse(cityDataPath);
		Iterator<List<String>> cityIterator = citiesData.iterator();
		
		while(cityIterator.hasNext()) {
			List<String> cityData = cityIterator.next();
			String cityName = cityData.get(0);
			String color = cityData.get(1);
			
			DeckComponent cityCard = new Card(cityName, color, cityCardType);
			playerCardDeck.addCard(cityCard, cityName);
			infectionCardDeck.addCard(cityCard, cityName);
			
			Integer population = Integer.parseInt(cityData.get(2));
			Integer x = Integer.parseInt(cityData.get(3));
			Integer y = Integer.parseInt(cityData.get(4));
			
			City city = new City(cityName, color, population, x, y);
			initializeCity(city);
			initializeInfectionCard(cityName);
			initializePlayerCard(Board.CardType.CITYCARD, cityName);
		}
		
		Iterator<String> eventIterator = eventCardNames.iterator();
		while(eventIterator.hasNext()) {
			String name = eventIterator.next();
			DeckComponent eventCard = new Card(name, null, eventCardType);
			playerCardDeck.addCard(eventCard, name);
		}
		
		cardDecks.addDeck(playerCardDeck, "PlayerCards");
		cardDecks.addDeck(infectionCardDeck, "InfectionCards");
		
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
		EventCardFactory eventCardFactory = new EventCardFactory(board);
		String cardName = board.messages.getString("Airlift");
		
		EventCard airlift = eventCardFactory.createEventCard(cardName);
		board.eventCards.put(cardName, airlift);
		
		cardName = board.messages.getString("Forecast");
		EventCard forcast = new ForecastEvent(board);
		board.eventCards.put(cardName, forcast);
		
		cardName = board.messages.getString("OneQuietNight");
		EventCard oneQuiteNight = new OneQuietNight(board);
		board.eventCards.put(cardName, oneQuiteNight);
		
		cardName = board.messages.getString("GovernmentGrant");
		EventCard governmentGrant = new GovernmentGrant(board);
		board.eventCards.put(cardName, governmentGrant);
		
		cardName = board.messages.getString("ResilientPopulation");
		EventCard resilientPopulation = new ResilientPopulation(board);
		board.eventCards.put(cardName, resilientPopulation);
		
		EventCardAction eventCardAction = new EventCardAction(board);
		board.eventCardAction = eventCardAction;
	}
	
	public void initializeCommands() {
		EpidemicCardAction epidemic = new EpidemicCardAction(board);
		DrawTwoPlayerCardsCommand drawTwoPlayerCard = new DrawTwoPlayerCardsCommand(epidemic);
		gameAction.setCommands(1, drawTwoPlayerCard);
		
		ActionCommand actionCommand = new ActionCommand();
		
		DriveCommand drive = new DriveCommand();
		actionCommand.addActionCommand(Board.ActionName.DRIVE, drive);
		DirectFlightCommand directFlight = new DirectFlightCommand();
		actionCommand.addActionCommand(Board.ActionName.DIRECTFLIGHT, directFlight);
		ShuttleFlightCommand shuttleFlight = new ShuttleFlightCommand();
		actionCommand.addActionCommand(Board.ActionName.SHUTTLEFLIGHT, shuttleFlight);
		CharterFlightCommand charterFlight = new CharterFlightCommand();
		actionCommand.addActionCommand(Board.ActionName.CHARTERFLIGHT, charterFlight);
		
		PlayEventCardCommand eventCard = new PlayEventCardCommand();
		actionCommand.addActionCommand(Board.ActionName.PLAYEVENTCARD, eventCard);
		CureDiseaseCommand discoverCUre = new CureDiseaseCommand();
		actionCommand.addActionCommand(Board.ActionName.CUREDISEASE, discoverCUre);
		TreatDiseaseCommand treatDisease = new TreatDiseaseCommand();
		actionCommand.addActionCommand(Board.ActionName.TREATDISEASE, treatDisease);
		BuildResearchStationCommand buildStation = new BuildResearchStationCommand();
		actionCommand.addActionCommand(Board.ActionName.BUILDRESEARCH, buildStation);
		ShareKnowledgeCommand shareKnowledge = new ShareKnowledgeCommand();
		actionCommand.addActionCommand(Board.ActionName.SHAREKNOWLEDGE, shareKnowledge);
		gameAction.setCommands(0, actionCommand);
		
		InfectionCardAction infectAction = new InfectionCardAction(board);
		InfectionCommand infect = new InfectionCommand(infectAction);
		gameAction.setCommands(2, infect);
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
		
		Player scientist = new Player(board, scientistData);
		Player medic = new Player(board, medicData);
		Player researcher = new Player(board, researcherData);
		Player dispatcher = new Player(board, dispatcherData);
		Player contingencyPlanner = new Player(board, contingencyPlannerData);
		Player operationsExpert = new Player(board, operationsExpertData);
		Player quarantineSpecialist = new Player(board, quarantineSpecialistData);
		
		scientist.buildStationModel = new StationBuilderNormal(scientistData, board);
		medic.buildStationModel = new StationBuilderNormal(medicData, board);
		researcher.buildStationModel = new StationBuilderNormal(researcherData, board);
		dispatcher.buildStationModel = new StationBuilderNormal(dispatcherData, board);
		contingencyPlanner.buildStationModel = new StationBuilderNormal(contingencyPlannerData, board);
		quarantineSpecialist.buildStationModel = new StationBuilderNormal(quarantineSpecialistData, board);
		operationsExpert.buildStationModel = new StationBuilderOperationsExpert(operationsExpertData, board);
		
		scientist.discoverCureModel = new DiscoverCureScientist(board.curedDiseases);
		medic.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		researcher.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		dispatcher.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		operationsExpert.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		contingencyPlanner.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialist.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		
		scientist.treatAction = new TreatNormal(scientistData);
		medic.treatAction = new TreatMedic(medicData);
		researcher.treatAction = new TreatNormal(researcherData);
		dispatcher.treatAction = new TreatNormal(dispatcherData);
		operationsExpert.treatAction = new TreatNormal(operationsExpertData);
		contingencyPlanner.treatAction = new TreatNormal(contingencyPlannerData);
		quarantineSpecialist.treatAction = new TreatNormal(quarantineSpecialistData);		
		
		operationsExpert.state = new OperationsExpertState(operationsExpertData);
		medic.state = new MedicState(medicData);
		contingencyPlanner.state = new ContingencyPlannerState(contingencyPlannerData);
		dispatcher.state = new DispatcherState();

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
