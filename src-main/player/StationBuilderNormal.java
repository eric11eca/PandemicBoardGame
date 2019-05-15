package player;

import data.Board;

public class StationBuilderNormal extends StationBuilder{
	
	public StationBuilderNormal(PlayerData actualPlayer,Board board) {
		super(actualPlayer,board);
	}
	
	@Override
	public void buildStation() {
		if(!playerData.hand.containsKey(playerData.location.cityName)) {
			throw new RuntimeException("NoCityCardException");
		}
		super.buildStation();
		playerData.hand.remove(playerData.location.cityName);
	}
}
