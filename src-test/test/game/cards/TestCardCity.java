package test.game.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Test;

import game.cards.CardCity;
import game.cards.Deck;
import game.city.City;
import mock.MockCityBuilder;

public class TestCardCity {
	@Test
	public void testAddToDeck() {
		City testCity = new MockCityBuilder().build();
		CardCity card = new CardCity(testCity);
		Deck deck = new Deck();
		assertTrue(deck.isEmpty());
		card.addToHand(deck);
		assertEquals(1, deck.size());
		assertEquals(card, deck.takeTopCard());
		deck.clear();
		assertTrue(deck.isEmpty());
		card.discard(deck);
		assertEquals(1, deck.size());
		assertEquals(card, deck.takeTopCard());
	}

	@Test
	public void testGetCity() {
		City testCity = new MockCityBuilder().build();
		CardCity card = new CardCity(testCity);

		assertTrue(card.getCity().isPresent());
		assertEquals(testCity, card.getCity().get());
	}

	@Test
	public void testGetEvent() {
		City testCity = new MockCityBuilder().build();
		CardCity card = new CardCity(testCity);
		assertFalse(card.getEvent().isPresent());
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		City testCity1 = new MockCityBuilder().build();
		CardCity card1 = new CardCity(testCity1);
		City testCity2 = new MockCityBuilder().name("different city").build();
		CardCity card2 = new CardCity(testCity2);
		City testCity3 = new MockCityBuilder().build();
		CardCity card3 = new CardCity(testCity3);

		assertFalse(card1.equals(null));
		assertFalse(card1.equals("not a card"));
		assertFalse(card1.equals(card2));
		assertTrue(card1.equals(card3));
		assertTrue(card1.equals(card1));
	}

	@Test
	public void testHashCode() {
		CardCity nullCard = new CardCity(null);
		assertEquals(31, nullCard.hashCode());
		City testCity1 = new MockCityBuilder().build();
		CardCity card1 = new CardCity(testCity1);
		assertEquals(Objects.hash(testCity1), card1.hashCode());
	}

}
