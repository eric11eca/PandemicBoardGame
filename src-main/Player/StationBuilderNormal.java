package Player;

import Initialize.Board;
import Initialize.City;

public class StationBuilderNormal extends StationBuilder{
	
	public StationBuilderNormal(Player actualPlayer,Board board) {
		super(actualPlayer,board);
	}

	public void buildStation() {
		if(player.location.researchStation) {
			throw new RuntimeException("This location already has research station.");
		}
		if(!player.hand.containsKey(player.location.cityName)) {
			throw new RuntimeException("You don't have the city card of the city you in!!");
		}
		City playerLocation = player.location;
		if(board.currentResearchStation.size() == 6) {
			City randomCity = player.buildStationModel.returnRandomResearchStationCity();
			board.currentResearchStation.remove(randomCity.cityName);
			randomCity.researchStation = false;
			board.currentResearchStation.put(playerLocation.cityName, playerLocation);
		}
		playerLocation.researchStation = true;
		player.consumeAction();
		player.hand.remove(playerLocation.cityName);
		
	}
}
