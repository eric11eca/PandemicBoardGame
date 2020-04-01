package buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import data.Board;
import game.city.City;
import gui.CityChooser;
import gui.GUI;
import initialize.GameSetup;

public class DriveListener implements ActionListener {
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;

	public DriveListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO move to domain layer
		/* ====Under Construction=== */
		HashSet<City> cities = new HashSet<>();
		cities.addAll(board.currentPlayer.playerData.location.neighbors);
		/* ====Under Construction=== */

		CityChooser cityChooser = new CityChooser(cities, gui, board.messages.getString("Drive"));
		cityChooser.letUserChooseACity().ifPresent(this::cityChosen);
	}

	private void cityChosen(City chosenCity) {
		board.driveDestination = chosenCity;
		board.actionName = Board.ActionName.DRIVE;
		gameSetup.oneTurn();
		gui.updateImage();
	}

}
