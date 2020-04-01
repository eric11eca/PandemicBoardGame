package buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import data.City;
import gui.CityChooser;
import gui.GUI;
import initialize.GameSetup;

public class CharterFlightListener implements ActionListener {
	private Board board;
	private JPanel panel;
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
			JOptionPane.showConfirmDialog(null, board.messages.getString("noCurrentCityCard"),
					board.messages.getString("noValidCard"), JOptionPane.OK_OPTION);
			return;
		}

		// TODO
		/* ====Extract & Move Method NEEDDED= */
		// This belongs to domain layer
		HashSet<City> cities = new HashSet<>();
		cities.addAll(board.cities.values());
		cities.remove(board.currentPlayer.playerData.location);
		/* ====Extract & Move Method NEEDDED= */

		CityChooser cityChooser = new CityChooser(cities, null, board.messages.getString("charterFlight"));
		cityChooser.letUserChooseACity().ifPresent(this::cityChosen);
	}

	private void cityChosen(City chosenCity) {
		board.cityCardNameCharter = chosenCity;
		board.actionName = Board.ActionName.CHARTERFLIGHT;
		gameSetup.oneTurn();
		gui.removePanel(panel);
		gui.updateImage();
	}

}
