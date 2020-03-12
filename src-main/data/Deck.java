package data;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Deck extends DeckComponent{
	public Map<String, DeckComponent> cards;
	
	public Deck() {
		cards = new HashMap<>();
	}
	
	public void addCard(DeckComponent card, String name) {
		cards.put(name, card);
	}
	
	public void removeCard(String name) {
		cards.remove(name);
	}
	
	public DeckComponent getCard(String name) {
		return cards.get(name);
	}
	
	public Iterator<String> cardIterator(){
		return cards.keySet().iterator();
	}
}
