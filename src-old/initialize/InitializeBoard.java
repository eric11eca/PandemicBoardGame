package initialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import cards.PlayerCard;
import data.Board;
import data.CityData;
import data.GameColor;
import game.Game;
import game.city.City;
import game.event.EventAirlift;
import game.event.EventForecast;
import game.event.EventGovernmentGrant;
import game.event.EventOneQuietNight;
import game.event.EventResilientPopulation;
import game.player.PlayerImpl;
import game.player.action.StationBuilderNormal;
import game.player.action.ActionTreatDiseaseMedic;
import game.player.action.ActionSkillOperationExpert;
import game.player.action.ActionEventCard;
import game.player.action.TreatNormal;
import game.player.action.ActionBuildStationWithoutCard;
import parse.CityLoader;
import player.DiscoverCureNormal;
import player.DiscoverCureScientist;
import player.PlayerData;
import playerAction.ContingencyPlannerAction;
import playerAction.MedicAction;
import render.RenderCity;

public class InitializeBoard {
	public Board board;
	public String cityDataPath = "CityData";
	public ThreadLocalRandom random;
	public ArrayList<String> eventCardNames;

	public InitializeBoard(Board mainBoard) {
		random = ThreadLocalRandom.current();
		this.board = mainBoard;
		this.eventCardNames = new ArrayList<String>();
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
		EventAirlift airlift = new EventAirlift(board);
		board.eventCards.put(board.messages.getString("Airlift"), airlift);
		EventForecast forcast = new EventForecast(board);
		board.eventCards.put(board.messages.getString("Forecast"), forcast);
		EventOneQuietNight oneQuiteNight = new EventOneQuietNight(board);
		board.eventCards.put(board.messages.getString("OneQuietNight"), oneQuiteNight);
		EventGovernmentGrant governmentGrant = new EventGovernmentGrant(board);
		board.eventCards.put(board.messages.getString("GovernmentGrant"), governmentGrant);
		EventResilientPopulation resilientPopulation = new EventResilientPopulation(board);
		board.eventCards.put(board.messages.getString("ResilientPopulation"), resilientPopulation);
		ActionEventCard eventCardAction = new ActionEventCard(board);
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
		operationsExpertData.buildStationModel = new ActionBuildStationWithoutCard(operationsExpertData, board);

		scientistData.discoverCureModel = new DiscoverCureScientist(board.curedDiseases);
		medicData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		researcherData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		dispatcherData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		operationsExpertData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		contingencyPlannerData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);
		quarantineSpecialistData.discoverCureModel = new DiscoverCureNormal(board.curedDiseases);

		scientistData.treatAction = new TreatNormal(board);
		medicData.treatAction = new ActionTreatDiseaseMedic();
		researcherData.treatAction = new TreatNormal(board);
		dispatcherData.treatAction = new TreatNormal(board);
		operationsExpertData.treatAction = new TreatNormal(board);
		contingencyPlannerData.treatAction = new TreatNormal(board);
		quarantineSpecialistData.treatAction = new TreatNormal(board);

		operationsExpertData.specialSkill = new ActionSkillOperationExpert(board, operationsExpertData);
		medicData.specialSkill = new MedicAction(board, medicData);
		contingencyPlannerData.specialSkill = new ContingencyPlannerAction(board, contingencyPlannerData);

		PlayerImpl scientist = new PlayerImpl(board, scientistData);
		PlayerImpl medic = new PlayerImpl(board, medicData);
		PlayerImpl researcher = new PlayerImpl(board, researcherData);
		PlayerImpl dispatcher = new PlayerImpl(board, dispatcherData);
		PlayerImpl contingencyPlanner = new PlayerImpl(board, contingencyPlannerData);
		PlayerImpl operationsExpert = new PlayerImpl(board, operationsExpertData);
		PlayerImpl quarantineSpecialist = new PlayerImpl(board, quarantineSpecialistData);

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
		Game.getInstance().initializeDiseaseCubes();
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
		Game.getInstance().takeCubeFromPool(city.getColor(), count);
		city.initializeDisease(count);
//		String color = city.getColor().compatibility_ColorString;
//		int numOfCubes = board.remainDiseaseCube.get(color);
//		city.diseaseCubes.put(color, count);
//		board.remainDiseaseCube.put(color, numOfCubes - count);
	}

	public void shuffleCards() {
		Collections.shuffle(board.validInfectionCards);
		Collections.shuffle(board.validPlayerCards);
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
