package player;

import data.Board;

public class Treat {
	public PlayerData playerData;
	public Board board;
	public int remainCube;

	public void treat(String diseaseColor) {
		int currentCityNumOfCube = playerData.location.diseaseCubes.get(diseaseColor);
		playerData.location.diseaseCubes.put(diseaseColor, remainCube);
		int remainDiseaseCube = board.remainDiseaseCube.get(diseaseColor);
		board.remainDiseaseCube.put(diseaseColor, remainDiseaseCube + currentCityNumOfCube - remainCube);
	}
	
}
