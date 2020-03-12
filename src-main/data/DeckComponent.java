package data;

import java.util.Iterator;

public abstract class DeckComponent {
	String name;
	
	public void addCard(DeckComponent card, String name) {
		throw new UnsupportedOperationException();
	}
	
	public void addDeck(DeckComponent deck, String name) {
		throw new UnsupportedOperationException();
	}
	
	public void removeCard() {
		throw new UnsupportedOperationException();
	}
	
	public void removeDeck(String name) {
		throw new UnsupportedOperationException();
	}
	
	public DeckComponent getDeck(String name) {
		throw new UnsupportedOperationException();
	}
	
	public DeckComponent getCard(String name) {
		throw new UnsupportedOperationException();
	}
	
	public Iterator<String> cardIterator(){
		throw new UnsupportedOperationException();
	}
	
	public Iterator<String> deckIterator() {
		throw new UnsupportedOperationException();
	}
	
	public String getName() {
		throw new UnsupportedOperationException();
	}
	
	public Board.CardType getType() {
		throw new UnsupportedOperationException();
	}
	
	public String getColor() {
		throw new UnsupportedOperationException();
	}
}
