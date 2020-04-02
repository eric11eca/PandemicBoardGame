package game.cards;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck<C extends Card> {
	private LinkedList<C> list;

	public Deck() {
		list = new LinkedList<>();
	}

	public void shuffle() {
		Collections.shuffle(list);
	}

	public void putOnTop(C card) {
		list.addFirst(card);
	}

	public void putAllOnTop(Collection<? extends C> cards) {
		for (C card : cards) {
			this.putOnTop(card);
		}
	}

	public void putOnBottom(C card) {
		list.addLast(card);
	}

	public int size() {
		return list.size();
	}

}
