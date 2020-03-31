package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import gui.GUI;
import initialize.GameSetup;

public class ShuttleFlightListener implements ActionListener {
	private Board board;
	private GameSetup gameSetup;
	private GUI gui;
	private JPanel panel;
	
	public ShuttleFlightListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ArrayList<String> cityOptions = new ArrayList<>();
		for (String i : board.currentResearchStation.keySet()) {
			cityOptions.add(i);
		}
		cityOptions.add(board.messages.getString("cancel")); 
		String[] cityNames = cityOptions.toArray(new String[cityOptions.size()]);
		String[] concatColorOptions = board.colorConcator.concatColor(cityNames, board.cities);
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
		String chosenCity = (options.getSelectedItem().toString()
				.split(board.messages.getString("lineConnector")))[0]; 
		if (chosenCity.equals(board.messages.getString("cancel"))) { 
			gui.removePanel(panel);
			return;
		} else {
			int choice = JOptionPane.showConfirmDialog(null, 
					board.messages.getString("flyConfirmation"), 
					board.messages.getString("flyConfirmation"), 
					JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				board.shuttleDestinationName = chosenCity;
				board.actionName = Board.ActionName.SHUTTLEFLIGHT;
				gameSetup.oneTurn();
				gui.removePanel(panel);
				gui.updateImage();
			} else {

			}
		}

	}
}
