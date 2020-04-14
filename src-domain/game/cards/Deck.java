package game.cards;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Implementation of a deck of cards
 *
 * @param <C> the deck type
 */
public class Deck {
	private LinkedList<Card> list;

	public Deck() {
		list = new LinkedList<>();
	}

	public void shuffle() {
		Collections.shuffle(list);
	}

	public void putOnTop(Card card) {
		list.addFirst(card);
	}

	public void putAllOnTop(Collection<? extends Card> cards) {
		for (Card card : cards) {
			this.putOnTop(card);
		}
	}

	public void putOnBottom(Card card) {
		list.addLast(card);
	}

	public int size() {
		return list.size();
	}

	public boolean removeCard(Card toRemove) {
		return list.remove(toRemove);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(Card card) {
		return list.contains(card);
	}

	public void forEach(Consumer<? super Card> action) {
		list.forEach(action);
	}

	public List<Card> getFilteredSubDeck(Predicate<? super Card> filter) {
		List<Card> list = new LinkedList<>();
		list.forEach(c -> {
			if (filter.test(c))
				list.add(c);
		});
		return list;
	}

	public Card takeBottomCard() {
		return list.removeLast();
	}

	public Card takeTopCard() {
		return list.removeFirst();
	}

	public List<Card> toList() {
		return getFilteredSubDeck(c -> true);
	}

}
