package Player;

import Initialize.City;

public class OperationsExpert extends Player{
	
	public OperationsExpert() {
		location = new City();
	}

	@Override
	public void removeAllCubes() {}

	@Override
	public void pickFromDiscardPlayerCard(String cardName) {}

	@Override
	public void buildResearchStation() {
		location.researchStation = true;
		action--;
	}

}
