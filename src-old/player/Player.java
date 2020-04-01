package player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cards.PlayerCard;
import data.Board;
import data.CityOLD;
import playerAction.BuildStation;
import playerAction.CharterFlight;
import playerAction.CureDisease;
import playerAction.DirectFlight;
import playerAction.Drive;
import playerAction.PlayEventCard;
import playerAction.PlayerAction;
import playerAction.ShareKnowledge;
import playerAction.ShuttleFlight;
import playerAction.TreatDisease;

public class Player {
	public PlayerData playerData;
	public Board board;
	public Map<Board.ActionName, PlayerAction> playerActions;

	public CityOLD destination;
	public String eventCardName;
	public String diseaseTobeTreated;
	public PlayerCard cityCard;
	public List<PlayerCard> cardsToCureDisease;

	public Player(Board board, PlayerData playerData) {
		this.board = board;
		this.playerData = playerData;

		playerActions = new HashMap<>();
		playerData.action = 4;

		playerActions.put(Board.ActionName.DRIVE, new Drive(board, this));
		playerActions.put(Board.ActionName.DIRECTFLIGHT, new DirectFlight(board, this));
		playerActions.put(Board.ActionName.CHARTERFLIGHT, new CharterFlight(board, this));
		playerActions.put(Board.ActionName.SHUTTLEFLIGHT, new ShuttleFlight(board, this));
		playerActions.put(Board.ActionName.CUREDISEASE, new CureDisease(board, this));
		playerActions.put(Board.ActionName.TREATDISEASE, new TreatDisease(board, this));
		playerActions.put(Board.ActionName.BUILDSTATION, new BuildStation(board, this));
		playerActions.put(Board.ActionName.PLAYEVENTCARD, new PlayEventCard(board, this));
		playerActions.put(Board.ActionName.SHAREKNOWLEDGE, new ShareKnowledge(board, this));
	}

	public void receiveCard(PlayerCard playerCard) {
		playerData.hand.put(playerCard.cardName, playerCard);
	}

	public void discardCard() {
		for (int i = 0; i < board.cardToBeDiscard.size(); i++) {
			String cardName = board.cardToBeDiscard.get(i);
			if (playerData.hand.containsKey(cardName)) {
				playerData.hand.remove(cardName);
			}
		}
		board.cardToBeDiscard.clear();
	}

	public void consumeAction() {
		playerData.action -= 1;
	}

	public void giveCard(Player giver, Player receiver, PlayerCard citycard) {
		if (giver.playerData.role != Board.Roles.RESEARCHER
				&& !citycard.cardName.equals(giver.playerData.location.getName())) {
			throw new RuntimeException("CanNotShareKnowledgeException");
		}
		board.cardToBeDiscard.add(citycard.cardName);
		giver.discardCard();
		receiver.receiveCard(citycard);
	}

	public PlayerAction getPlayerAction(Board.ActionName actionType) {
		PlayerAction action = playerActions.get(actionType);
		if (action == null) {
			throw new IllegalArgumentException("Invalid action type: " + actionType);
		}
		return action;
	}

	public boolean canCharterFlight() {
		Set<String> hand = playerData.hand.keySet();
		String playerLocationCityName = playerData.location.getName();
		return hand.contains(playerLocationCityName);
	}
}
