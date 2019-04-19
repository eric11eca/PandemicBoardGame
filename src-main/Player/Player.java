package Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Card.EventCardAction;
import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public abstract class Player {
	public Map<String, PlayerCard> hand = new HashMap<>();
	public City location;
	public int action = 4;
	public PlayerCard specialEventCard;
	public String cardToBeDiscard;
	
	public boolean handOverFlow = false;
	public Board board;
	public Random random;
	DiscoverCure discoverCure;
	public StationBuilder buildStationModel;

	public Player(Board gameBoard) {
		this(gameBoard, new Random());
	}

	public Player(Board gameBoard, Random random) {
		board = gameBoard;
		this.random = random;
	}

	public void receiveCard(PlayerCard playerCard) {
		if (hand.size() >= 7) {
			handOverFlow = true;
			discardCard(cardToBeDiscard);
		} else {
			hand.put(playerCard.cardName, playerCard);
		}
	}

	public boolean useEventCard(String cardName) {
		boolean cardUsed = false;
		if (cardName.equals(specialEventCard.cardName)) {
			EventCardAction eventCardAction = new EventCardAction(board, specialEventCard);
			cardUsed = eventCardAction.excuteEventCard();
			if (cardUsed) {
				this.specialEventCard = null;
			}
		} else {
			PlayerCard card = hand.get(cardName);
			EventCardAction eventCardAction = new EventCardAction(board, card);
			cardUsed = eventCardAction.excuteEventCard();
		}
		return cardUsed;
	}

	public void discardCard(String cardName) {
		if (hand.containsKey(cardName)) {
			PlayerCard playerCard = hand.get(cardName);
			hand.remove(cardName);
			board.discardPlayerCard.put(cardName, playerCard);
		} else {
			throw new RuntimeException("This card does not exist in the hand");
		}
	}

	public void drive(City destination) {
		if (location.neighbors.containsKey(destination.cityName)) {
			location = destination;
		} else {
			throw new RuntimeException("Invalid destination: Not a neighbour!!");
		}
	}

	public void directFlight(PlayerCard cityCard) {
		if (cityCard.cardName.equals(location.cityName)) {
			throw new IllegalArgumentException("Cannot direct flight to current city");
		} else if (cityCard.cardType == Board.CardType.CITYCARD) {
			hand.remove(cityCard.cardName);
			consumeAction();
			location = board.cities.get(cityCard.cardName);
		} else {
			throw new IllegalArgumentException("Illegal Argument Type");
		}
	}

	public void consumeAction() {
		if (action <= 0) {
			throw new RuntimeException("NO MORE ACTIONS!");
		}
		action--;
	}

	public void characterFlight(PlayerCard cityCard) {
		String cityCardName = cityCard.cardName;
		String playerLocationCityName = location.cityName;
		if (cityCard.cardType != Board.CardType.CITYCARD) {
			throw new IllegalArgumentException("Not a cityCard");
		} else {
			if (cityCardName.equals(playerLocationCityName)) {
				City destination = randomDestination();
				while (destination.cityName.equals(playerLocationCityName)) {
					destination = randomDestination();
				}
				location = destination;
				discardCard(cityCard.cardName);
				consumeAction();
			} else {
				throw new IllegalArgumentException("The name of city card is different from your location");
			}
		}
	}

	public City randomDestination() {
		Collection<City> cities = board.cities.values();
		int randomInt = random.nextInt(cities.size());
		City destination = (cities.toArray(new City[0]))[randomInt];
		return destination;
	}

	public void shuttleFlight(City destination) {
		if (location.researchStation) {
			if (destination.researchStation) {
				location = destination;
				consumeAction();
			} else {
				throw new RuntimeException("Invalid shuttle flight: Destination doesn't have the station.");
			}
		} else {
			throw new RuntimeException("Invalid shuttle flight: Current location doesn't hava the station.");
		}
	}

	public void treat(String diseaseColor) {
		int numOfDiseaseCubes = location.diseaseCubes.get(diseaseColor);
		if (numOfDiseaseCubes == 0) {
			throw new RuntimeException("The number of " + diseaseColor + "Cube is zero");
		}
		int removeCounts = 1;
		if (board.curedDiseases.contains(diseaseColor)) {
			removeCounts = numOfDiseaseCubes;
		}
		location.diseaseCubes.put(diseaseColor, numOfDiseaseCubes - removeCounts);
		consumeAction();
	}

	public void discoverCure(ArrayList<PlayerCard> cards) {
		if (isResearchStation()) {
			if (discoverCure.discoverCure(cards)) {
				for (PlayerCard playercard : cards) {
					discardCard(playercard.cardName);
				}
				consumeAction();
			}
			
			if(board.curedDiseases.size() == 4) {
				board.gameEnd = true;
				board.playerWin = true;
			}
			
		} else {
			throw new RuntimeException("You are not at the research Station!!");
		}
	}

	private boolean isResearchStation() {
		return location.researchStation;
	}

	public void buildStation() {
		buildStationModel.buildStation();
	}
}
