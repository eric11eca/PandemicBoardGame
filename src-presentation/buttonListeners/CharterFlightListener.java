package buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import data.Board;
import data.CityOLD;
import gui.CityChooser;
import gui.GUI;
import initialize.GameSetup;

public class CharterFlightListener implements ActionListener {
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;

	public CharterFlightListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!board.currentPlayer.canCharterFlight()) {
			gui.displayMessage(board.messages.getString("noValidCard"), board.messages.getString("noCurrentCityCard"));
			return;
		}

		// TODO
		/* ====Extract & Move Method NEEDDED= */
		// This belongs to domain layer
		HashSet<CityOLD> cities = new HashSet<>();
		cities.addAll(board.cities.values());
		cities.remove(board.currentPlayer.playerData.location);
		/* ====Extract & Move Method NEEDDED= */

		CityChooser cityChooser = new CityChooser(cities, gui, board.messages.getString("charterFlight"));
		cityChooser.letUserChooseACity().ifPresent(this::cityChosen);
	}

	private void cityChosen(CityOLD chosenCity) {
		board.cityCardNameCharter = chosenCity;
		board.actionName = Board.ActionName.CHARTERFLIGHT;
		gameSetup.oneTurn();
		gui.updateImage();
	}

}
