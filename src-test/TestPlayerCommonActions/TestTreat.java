package TestPlayerCommonActions;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Initialize.Board;
import Initialize.City;
import Player.Medic;
import Player.Player;

public class TestTreat {
	Player player;
	Board board;
	City city;
	String blue = "BLUE";
	String yellow = "YELLOW";

	@Before
	public void setup() {
		board = new Board();
		player = new Medic(board);
		city = new City();
		player.location = city;
		player.location.diseaseCubes.put(blue, 0);
	}

	@Test
	public void testNormalTreat() {
		city.diseaseCubes.put(blue, 1);
		player.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		assertEquals(0, numOfBlueCubes);
		assertEquals(3, player.action);
	}

	@Test(expected = RuntimeException.class)
	public void testTreatWithZeroDiseaseCube() {
		player.treat(blue);
	}

	@Test
	public void testTreatWithSameColorCure() {
		city.diseaseCubes.put(blue, 3);
		board.curedDiseases.add(blue);
		player.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		assertEquals(0, numOfBlueCubes);
	}

	@Test
	public void testTreateWithDifferentColorCure() {
		city.diseaseCubes.put(blue, 3);
		board.curedDiseases.add(yellow);
		player.treat(blue);
		int numOfBlueCubes = city.diseaseCubes.get(blue);
		assertEquals(2, numOfBlueCubes);
	}
}
