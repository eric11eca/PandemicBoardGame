package Player;

import Initialize.Board;

public class StationBuilderNormal extends StationBuilder{
	
	public StationBuilderNormal(Player actualPlayer,Board board) {
		super(actualPlayer,board);
	}
	
	@Override
	public void buildStation() {
		if(!player.hand.containsKey(player.location.cityName)) {
			throw new RuntimeException("NoCityCardException");
		}
		super.buildStation();
		player.hand.remove(player.location.cityName);
		
	}
}
