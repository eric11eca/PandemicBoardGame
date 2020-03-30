package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import initialize.GameSetup;
import panel.GUI;

public class CharterFlightListener extends Observable implements ActionListener {
	public Observer charterFlightObserver;
	public String chosenCity;
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
			JOptionPane.showConfirmDialog(null, 
					board.messages.getString("noCurrentCityCard"), 
					board.messages.getString("noValidCard"),
					JOptionPane.OK_OPTION);
			return;
		}

		String[] cityOptions = new String[47];
		int counter = 0;
		for (String cityname : board.cities.keySet()) {
			String locationCityName = board.currentPlayer.playerData.location.cityName;
			if (!cityname.equals(locationCityName)) {
				cityOptions[counter] = cityname;
				counter++;
			}
		}
		String[] concatColorOptions = board.colorConcator.concatColor(cityOptions, board.cities);
		JComboBox<String> options = new JComboBox<String>(concatColorOptions);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirmCity(options);
			}
		});
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);
	}

	protected void confirmCity(JComboBox<String> options) {
		chosenCity = (options.getSelectedItem().toString().
				split(board.messages.getString("lineConnector")))[0]; 
		int choice = JOptionPane.showConfirmDialog(null, 
				board.messages.getString("flyConfirmation"), 
				board.messages.getString("charterFlight"),
				JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			this.setChanged();
			this.notifyObservers();
			gameSetup.oneTurn();
			gui.removePanel(panel);
			gui.updateImage();
			this.clearChanged();
		} 
	}
}
