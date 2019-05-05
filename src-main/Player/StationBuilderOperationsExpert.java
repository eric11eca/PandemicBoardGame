package Player;

import Initialize.Board;

public class StationBuilderOperationsExpert extends StationBuilder{
	public StationBuilderOperationsExpert(PlayerData actualPlayer, Board gameBoard) {
		super(actualPlayer, gameBoard);
	}
	
	@Override
	public void buildStation() {
		if(!playerData.hand.containsKey(playerData.location.cityName)) {
			throw new RuntimeException("NoCityCardException");
		}
		super.buildStation();
	} 

}
