package player;

import java.util.List;

import PlayerAction.CharterFlight;
import PlayerAction.CommonPlayerAction;
import PlayerAction.DirectFlight;
import PlayerAction.DiscoverCure;
import PlayerAction.Drive;
import PlayerAction.Mobility;
import PlayerAction.ShareKnowledge;
import PlayerAction.ShuttleFlight;
import PlayerAction.StationBuilder;
import PlayerAction.Treat;
import SpeciaoPlayerAction.State;
import cards.PlayerCard;
import data.Board;
import data.City;

public class Player {
	public Board board;
	public PlayerData playerData;
	
	//initialized outside of the class
	public State state;
	public DiscoverCure discoverCureModel;
	public StationBuilder buildStationModel;
	public Treat treatAction;
	
	////initialized inside of the class
	public Mobility drive;
	public Mobility directFlight;
	public Mobility charterFlight;
	public Mobility shuttleFlight;
	public ShareKnowledge shareKnowledge;
	public CommonPlayerAction commonAction;

	public Player(Board gameBoard, PlayerData data) {
		board = gameBoard;
		playerData = data;
		playerData.action = 4;
		
		drive = new Drive(playerData);
		directFlight = new DirectFlight(playerData);
		charterFlight = new CharterFlight(playerData);
		shuttleFlight = new ShuttleFlight(playerData);
		shareKnowledge = new ShareKnowledge();
		commonAction = new CommonPlayerAction(playerData);
	}

	public void receiveCard(PlayerCard playerCard) {
		commonAction.receiveCard(playerCard);
	}

	public void useEventCard(String cardName) {
		commonAction.useEventCard(cardName);
	}

	public void drive(City destination) {
		 drive.move(destination);
		 commonAction.consumeAction();
	}

	public void directFlight(PlayerCard cityCard) {
		directFlight.cityCard = cityCard;
		directFlight.move(null);
		commonAction.discardCard();
		commonAction.consumeAction();
	}

	public void charterFlight() {
		charterFlight.move(null);
		commonAction.discardCard();
		commonAction.consumeAction();
	}

	public void shuttleFlight(City destination) {
		shuttleFlight.move(destination);
		commonAction.consumeAction();
	}

	public void treat(String diseaseColor) {
		treatAction.treat(diseaseColor);
		commonAction.eradicate(diseaseColor);
		commonAction.consumeAction();
	}
	
	public void buildStation() {
		buildStationModel.buildStation();
		commonAction.consumeAction();
	}

	public void shareKnowledge() {
		shareKnowledge.shareKnowledge(this);
		commonAction.consumeAction();
	}
	
	public void specialSkill() {
		state.useSpecialSkill();
	}

	public void discoverCure(List<PlayerCard> cardsToCureDisease) {
		boolean isResearchStation = playerData.location.researchStation;
		if (isResearchStation) {
			if (discoverCureModel.discover(cardsToCureDisease)) {
				for (PlayerCard playercard : cardsToCureDisease) {
					board.cardToBeDiscard.add(playercard.cardName);
				}
				commonAction.consumeAction();
			}
			if (board.curedDiseases.size() == 4) {
				throw new RuntimeException("PlayerWinException");
			}
			
			commonAction.eradicate(cardsToCureDisease.get(0).color);
			commonAction.discardCard();

		} else {
			throw new RuntimeException("NoStationException");
		}
	}
}
