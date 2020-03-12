package PlayerAction;

import data.Board;
import player.PlayerData;

public abstract class Treat{
	public Board board;
	public PlayerData playerData;
	public int remainCube;

	public void treat(String diseaseColor) {
		int currentCityNumOfCube = playerData.location.diseaseCubes.get(diseaseColor);
		playerData.location.diseaseCubes.put(diseaseColor, remainCube);
		int remainDiseaseCube = board.remainDiseaseCube.get(diseaseColor);
		int newCubeNumber = remainDiseaseCube + currentCityNumOfCube - remainCube;
		board.remainDiseaseCube.put(diseaseColor, newCubeNumber);
	}
}
