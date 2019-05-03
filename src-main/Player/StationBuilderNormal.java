package Player;

import Initialize.Board;

public class StationBuilderNormal extends StationBuilder{
	
	public StationBuilderNormal(PlayerData actualPlayer,Board board) {
		super(actualPlayer,board);
	}
	
	@Override
	public void buildStation() {
		if(!playerData.hand.containsKey(playerData.location.cityName)) {
			throw new RuntimeException("You don't have the city card of the city you in!!");
		}
		super.buildStation();
		playerData.hand.remove(playerData.location.cityName);
		
	}
}
