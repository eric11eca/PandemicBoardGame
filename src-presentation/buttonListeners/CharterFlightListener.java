package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
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
		Set<String> hand = board.currentPlayer.playerData.hand.keySet();
		String playerLocationCityName = board.currentPlayer.playerData.location.cityName;
		if (!hand.contains(playerLocationCityName)) {
			JOptionPane.showConfirmDialog(null, board.messages.getString("noCurrentCityCard"),
					board.messages.getString("noValidCard"), JOptionPane.OK_OPTION);
			return;
		}

		HashSet<City> cities = new HashSet<>();
		cities.addAll(board.cities.values());
		cities.remove(board.currentPlayer.playerData.location);
//		String[] cityOptions = new String[47];
//		int counter = 0;
//		for (String cityname : board.cities.keySet()) {
//			String locationCityName = board.currentPlayer.playerData.location.cityName;
//			if (!cityname.equals(locationCityName)) {
//				cityOptions[counter] = cityname;
//				counter++;
//			}
//		}
//		String[] concatColorOptions = board.colorConcator.concatColor(cityOptions, board.cities);
//		JComboBox<String> options = new JComboBox<String>(concatColorOptions);
//		options.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				confirmCity(options);
//			}
//		});
//		panel = new JPanel();
//		panel.add(options);
//		gui.addPanel(panel, BorderLayout.CENTER);
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

//	protected void confirmCity(JComboBox<String> options) {
//		String chosenCity = (options.getSelectedItem().toString().split(board.messages.getString("lineConnector")))[0];
//		int choice = JOptionPane.showConfirmDialog(null, board.messages.getString("flyConfirmation"),
//				board.messages.getString("charterFlight"), JOptionPane.YES_NO_OPTION);
//		if (choice == 0) {
//
//		} else {
//
//		}
//
//	}

}
