package mock;

import static org.junit.Assert.fail;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
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
		selectCardsToDiscardImpl = this::errBiFunction;
		arrangeCardsImpl = this::errBiConsumer;
		displayCardImpl = this::errConsumer;
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

	public MockInteraction implementSelectCardsToDiscard(MethodDiscardCards selectCardsToDiscardImpl) {
		this.selectCardsToDiscardImpl = selectCardsToDiscardImpl;
		return this;
	}

	public MockInteraction implementArrangeCards(MethodArrangeCards arrangeCardsImpl) {
		this.arrangeCardsImpl = arrangeCardsImpl;
		return this;
	}

	public MockInteraction implementDisplayCities(MethodDisplayCards displayCityImpl) {
		this.displayCardImpl = displayCityImpl;
		return this;
	}

	//@formatter:off
	private MethodSelectColorFrom selectColorFromImpl;
	private MethodSelectPlayerFrom selectPlayerFromImpl;
	private MethodSelectCardsFrom selectCardsFromImpl;
	private MethodSelectCityFrom selectCityFromImpl;
	private MethodDiscardCards selectCardsToDiscardImpl;
	private MethodArrangeCards arrangeCardsImpl;
	private MethodDisplayCards displayCardImpl;

	public interface MethodSelectColorFrom extends BiConsumer<Set<GameColor>, Consumer<GameColor>> {	}
	public interface MethodSelectPlayerFrom extends BiConsumer<List<Player>, Consumer<Player>> {	}
	public interface MethodSelectCardsFrom {
		void selectCardsFrom(int number, List<Card> cards, Consumer<List<Card>> callback);
	}
	public interface MethodSelectCityFrom extends BiConsumer<Set<City>,Consumer<City>>{}
	public interface MethodArrangeCards{
		void arrangeCards(List<Card> cards, Consumer<List<Card>> callback);
	}
	public interface MethodDisplayCards extends Consumer<List<Card>>{}
	public interface MethodDiscardCards extends BiFunction<Integer,List<Card>,List<Card>>{}
	@Override
	public void selectColorFrom(Set<GameColor> colors,String title, Consumer<GameColor> callback) 
	{selectColorFromImpl.accept(colors, callback);}
	@Override
	public void selectPlayerFrom(List<Player> players,String title, Consumer<Player> callback) 
	{selectPlayerFromImpl.accept(players, callback);}
	@Override
	public void selectCardsFrom(int number, List<Card> cards, String title,Consumer<List<Card>> callback)
	{selectCardsFromImpl.selectCardsFrom(number, cards, callback);}
	@Override
	public void selectCityFrom(Set<City> cities, String title,Consumer<City> callback) 
	{selectCityFromImpl.accept(cities, callback);}
	@Override
	public List<Card> selectCardsToDiscard(int number, List<Card> cards,String title) 
	{return selectCardsToDiscardImpl.apply(number, cards);}
	@Override
	public void arrangeCards(List<Card> cards,String title, Consumer<List<Card>> callback) 
	{arrangeCardsImpl.arrangeCards(cards, callback);}
	@Override
	public void displayCards(List<Card> cards,String title)
	{displayCardImpl.accept(cards);}

	//@formatter:on
	private <T> void errConsumer(T t) {
		mockFail();
	}

	private <T, R> void errBiConsumer(T t, R r) {
		mockFail();
	}

	private <T, R, I> void errTriConsumer(T t, R r, I i) {
		mockFail();
	}

	private <T, R, I> I errBiFunction(T t, R r) {
		mockFail();
		return null;
	}

	private void mockFail() {
		fail("This method is not mocked in PlayerInteraction");
	}

}
