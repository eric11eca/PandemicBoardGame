package data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CardDecks extends DeckComponent{
	public Map<String, DeckComponent> decks;
    
	public CardDecks() {
		decks = new HashMap<>();
	}
	
	public void addDeck(DeckComponent deck, String name) {
		this.decks.put(name, deck);
	}
	
	public void removeDeck(String name) {
		this.decks.remove(name);
	}
	
	public DeckComponent getDeck(String name) {
		return this.decks.get(name);
	}
	
	public Iterator<String> deckIterator() {
		return this.decks.keySet().iterator();
	}
}
