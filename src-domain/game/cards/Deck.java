package game.cards;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

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

	public boolean removeCard(C toRemove) {
		return list.remove(toRemove);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void clear() {
		list.clear();
	}

	public boolean contains(C card) {
		return list.contains(card);
	}

	public void forEach(Consumer<? super C> action) {
		list.forEach(action);
	}

	public List<C> getFilteredSubDeck(Function<? super C, Boolean> filter) {
		List<C> list = new LinkedList<>();
		list.forEach(c -> {
			if (filter.apply(c))
				list.add(c);
		});
		return list;
	}

	public C takeBottomCard() {
		return list.removeLast();
	}

	public C takeTopCard() {
		return list.removeFirst();
	}

}
