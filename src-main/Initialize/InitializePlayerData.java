package Initialize;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import Card.PlayerCard;
import Player.Player;

public class InitializePlayerData {
	Board board;
	String prefix = "Player.";

	public InitializePlayerData(Board board) {
		this.board = board;
	}

	public void addRole() {
		board.totalRoles.add("Dispatcher");
		board.totalRoles.add("Medic");
		board.totalRoles.add("OperationsExpert");
		board.totalRoles.add("QuarantineSpecialist");
		board.totalRoles.add("Researcher");
		board.totalRoles.add("Scientist");
		board.totalRoles.add("ContingencyPlanner");
	}

	public void createPlayers() {
		Collections.shuffle(board.totalRoles);

		for (int i = 0; i < board.playernumber; i++) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(prefix + board.totalRoles.get(i));
			} catch (ClassNotFoundException e2) {
				e2.printStackTrace();
				throw new RuntimeException(e2);
			}
			Constructor<?> constructor = null;
			try {
				constructor = clazz.getConstructor();
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
				throw new RuntimeException(e1);
			}
			try {
				Player role = (Player) constructor.newInstance();
				board.currentPlayers.add(role);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
	}

	public void drawHandCard() {
		for(int i = 0; i < board.playernumber; i++){
			for(int j = 0; j < board.initialhandcard; j++){
				int topOfDeck = board.validPlayerCard.size() - 1;
				PlayerCard playercard = board.validPlayerCard.remove(topOfDeck);
				board.currentPlayers.get(i).hand.add(playercard); 
		}
	}
		
	}

}
