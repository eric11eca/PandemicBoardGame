package Player;

import java.util.ArrayList;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.City;

public abstract class Player {

	public ArrayList<PlayerCard> hand = new ArrayList<>();
	public City location;
	public int action;
	Board board;
	
	public Player(Board gameBoard){
		board = gameBoard;
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

	public void isValid() {

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
		action--;
	}

}
