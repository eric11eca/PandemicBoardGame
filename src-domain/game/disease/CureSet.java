package game.disease;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import game.GameColor;
import game.GameState;

public class CureSet implements Set<GameColor> {
	private GameState game;
	private Set<GameColor> underlying;

	public CureSet(GameState game, Set<GameColor> underlying) {
		super();
		this.game = game;
		this.underlying = underlying;
	}

	private void checkWin() {
		for (GameColor color : GameColor.values()) {
			if (!contains(color))
				return;
		}
		game.triggerWin();
	}

	@Override
	public boolean add(GameColor e) {
		boolean added = underlying.add(e);
		checkWin();
		return added;
	}
	/* Decorator Methods */

	@Override
	public boolean addAll(Collection<? extends GameColor> c) {
		return underlying.addAll(c);
	}

	@Override
	public void clear() {
		underlying.clear();
	}

	@Override
	public boolean contains(Object o) {
		return underlying.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return underlying.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return underlying.equals(o);
	}

	@Override
	public int hashCode() {
		return underlying.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return underlying.isEmpty();
	}

	@Override
	public Iterator<GameColor> iterator() {
		return underlying.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return underlying.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return underlying.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return underlying.retainAll(c);
	}

	@Override
	public int size() {
		return underlying.size();
	}

	@Override
	public Object[] toArray() {
		return underlying.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return underlying.toArray(a);
	}

}
