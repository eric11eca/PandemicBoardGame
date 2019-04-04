package TestPlayers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestMedic {
	Medic medic;
	Board board;
	@Before
	public void setup() {
		board = new Board();
		medic = new Medic(board);
		City city = new City();
		city.cityName = "a";
		medic.location = city;
	}
	
	@Test
	public void testRemoveAllUncuredCubes() {
		medic.location.diseaseCubes.put("yellow", 2);
		medic.location.diseaseCubes.put("blue", 1);
		assertFalse(medic.location.diseaseCubes.isEmpty());
		medic.removeAllCubes();
		assertTrue(medic.location.diseaseCubes.isEmpty());
		assertEquals(3, medic.action);
	}
	
	@Test
	public void testRemoveAllCuredCubes() {
		medic.location.diseaseCubes.put("yellow", 2);
		medic.location.diseaseCubes.put("blue", 1);
		board.curedDiseases.add("yellow");
		board.curedDiseases.add("blue");
		medic.removeAllCubes();
		assertTrue(medic.location.diseaseCubes.isEmpty());
		assertEquals(medic.action, 4);
	}
}
