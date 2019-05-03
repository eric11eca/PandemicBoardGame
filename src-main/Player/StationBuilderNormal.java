package Player;

import Initialize.Board;

public class StationBuilderNormal extends StationBuilder{
	
<<<<<<< HEAD
	public StationBuilderNormal(PlayerData actualPlayer,Board board) {
=======
	public StationBuilderNormal(Player actualPlayer,Board board) {
>>>>>>> origin/fixGui
		super(actualPlayer,board);
	}
	
	@Override
	public void buildStation() {
<<<<<<< HEAD
		if(!playerData.hand.containsKey(playerData.location.cityName)) {
			throw new RuntimeException("You don't have the city card of the city you in!!");
		}
		super.buildStation();
		playerData.hand.remove(playerData.location.cityName);
=======
		if(!player.hand.containsKey(player.location.cityName)) {
			throw new RuntimeException("NoCityCardException");
		}
		super.buildStation();
		player.hand.remove(player.location.cityName);
>>>>>>> origin/fixGui
		
	}
}
