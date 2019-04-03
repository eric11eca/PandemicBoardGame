package Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public abstract class Player {

	public ArrayList<PlayerCard> hand = new ArrayList<>();
	public City location;
	public int action = 4;
	Board board;
	Random random;

	public Player(Board gameBoard) {
		this(gameBoard, new Random());
	}

	public Player(Board gameBoard, Random random) {
		board = gameBoard;
		this.random = random;
	}

	public void receiveCard(PlayerCard playercard) {
		if (hand.size() >= 7) {
			// somehow discard card
		}
		hand.add(playercard);
	}

	public boolean discardCard(PlayerCard playercard) {
		if (hand.contains(playercard)) {
			hand.remove(playercard);
			return true;
		}
		return false;
	}

	public void drive(City destination) {
		if (location.neighbors.contains(destination)) {
			location = destination;
		} else {
			throw new RuntimeException("Invalid destinaion: Not a neighbor!!");
		}
	}

	public void move(City destination) {
		if (location.neighbors.contains(destination)) {
			location = destination;
		}
	}

	public void directFlight(PlayerCard cityCard) {
		if (cityCard.cardName.equals(location.cityName)) {
			throw new IllegalArgumentException("Cannot direct flight to current city");
		} else if (cityCard.cardType == Board.CardType.CITYCARD) {
			hand.remove(cityCard);
			consumeAction();
			location = board.cities.get(cityCard.cardName);
		} else {
			throw new IllegalArgumentException("Illegal Argument Type");
		}
	}

	public void consumeAction() {
		if (action <= 0) {
			throw new RuntimeException("Run out of Actions");
		} else {
			action--;
		}
	}

	public void characterFlight(PlayerCard cityCard) {
		String cityCardName = cityCard.cardName;
		String playerLocationCityName = location.cityName;
		if (cityCard.cardType != Board.CardType.CITYCARD) {
			throw new IllegalArgumentException("Not a cityCard");
		} else {
			if (cityCardName.equals(playerLocationCityName)) {
				City destination = randomDestination();
				System.out.println(destination.cityName);
				while (destination.cityName.equals(playerLocationCityName)) {
					destination = randomDestination();
					System.out.println(destination.cityName);
				}
				System.out.println("Before assign location: " + destination.cityName);
				location = destination;
				discardCard(cityCard);
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

}
