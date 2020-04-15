package test;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import game.GameColor;
import game.cards.Card;
import game.city.City;
import game.player.Player;
import game.player.PlayerInteraction;

public class MockInteraction implements PlayerInteraction {
	public MockInteraction() {
		selectColorFromImpl = this::errBiConsumer;
		selectPlayerFromImpl = this::errBiConsumer;
		selectCardsFromImpl = this::errTriConsumer;
		selectCityFromImpl = this::errBiConsumer;
		selectCardsToDiscardImpl = this::errTriConsumer;
		arrangeCardsImpl = this::errBiConsumer;
	}

	public MockInteraction implementSelectColorFrom(MethodSelectColorFrom selectColorFromImpl) {
		this.selectColorFromImpl = selectColorFromImpl;
		return this;
	}

	public MockInteraction implementSelectPlayerFrom(MethodSelectPlayerFrom selectPlayerFromImpl) {
		this.selectPlayerFromImpl = selectPlayerFromImpl;
		return this;
	}

	public MockInteraction implementSelectCardsFrom(MethodSelectCardsFrom selectCardsFromImpl) {
		this.selectCardsFromImpl = selectCardsFromImpl;
		return this;
	}

	public MockInteraction implementSelectCityFrom(MethodSelectCityFrom selectCityFromImpl) {
		this.selectCityFromImpl = selectCityFromImpl;
		return this;
	}

	public <T extends Card> MockInteraction implementSelectCardsToDiscard(
			MethodSelectCardsFrom selectCardsToDiscardImpl) {
		this.selectCardsToDiscardImpl = selectCardsToDiscardImpl;
		return this;
	}

	public <T extends Card> MockInteraction implementArrangeCards(MethodArrangeCards arrangeCardsImpl) {
		this.arrangeCardsImpl = arrangeCardsImpl;
		return this;
	}

	//@formatter:off
	private MethodSelectColorFrom selectColorFromImpl;
	private MethodSelectPlayerFrom selectPlayerFromImpl;
	private MethodSelectCardsFrom selectCardsFromImpl;
	private MethodSelectCityFrom selectCityFromImpl;
	private MethodSelectCardsFrom selectCardsToDiscardImpl;
	private MethodArrangeCards arrangeCardsImpl;

	public interface MethodSelectColorFrom extends BiConsumer<Set<GameColor>, Consumer<GameColor>> {	}
	public interface MethodSelectPlayerFrom extends BiConsumer<List<Player>, Consumer<Player>> {	}
	public interface MethodSelectCardsFrom {
		void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback);
	}
	public interface MethodSelectCityFrom extends BiConsumer<Set<City>,Consumer<City>>{}
	public interface MethodArrangeCards{
		void arrangeCards(List<Card> cards, Consumer<List<Card>> callback);
	}
	@Override
	public void selectColorFrom(Set<GameColor> colors, Consumer<GameColor> callback) 
	{selectColorFromImpl.accept(colors, callback);}
	@Override
	public void selectPlayerFrom(List<Player> players, Consumer<Player> callback) 
	{selectPlayerFromImpl.accept(players, callback);}
	@Override
	public void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback)
	{selectCardsFromImpl.selectCardsFrom(number, cards, callback);}
	@Override
	public void selectCityFrom(Set<City> cities, Consumer<City> callback) 
	{selectCityFromImpl.accept(cities, callback);}
	@Override
	public void selectCardsToDiscard(int number, List<Card> cards, Consumer<List<Card>> callback) 
	{selectCardsToDiscardImpl.selectCardsFrom(number, cards, callback);}
	@Override
	public void arrangeCards(List<Card> cards, Consumer<List<Card>> callback) 
	{arrangeCardsImpl.arrangeCards(cards, callback);}
	//@formatter:on
	private <T, R> void errBiConsumer(T t, R r) {
		mockFail();
	}

	private <T, R, I> void errTriConsumer(T t, R r, I i) {
		mockFail();
	}

	private void mockFail() {
		fail("This method is not mocked in PlayerInteraction");
	}

}
