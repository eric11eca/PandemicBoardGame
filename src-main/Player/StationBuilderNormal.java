package Player;

import Initialize.Board;

public class StationBuilderNormal extends StationBuilder{
	
	public StationBuilderNormal(Player actualPlayer,Board board) {
		super(actualPlayer,board);
	}
	
	@Override
	public void buildStation() {
		if(!player.hand.containsKey(player.location.cityName)) {
			throw new RuntimeException("You don't have the city card of the city you in!!");
		}
		super.buildStation();
		player.hand.remove(player.location.cityName);
		
	}
}
