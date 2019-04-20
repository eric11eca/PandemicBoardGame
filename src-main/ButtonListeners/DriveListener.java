package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class DriveListener implements ActionListener {
	private JPanel panel;
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;
	String chosenCity;

	public DriveListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Set<String> cityOptions = board.currentPlayer.location.neighbors.keySet();
		String[] cities = new String[cityOptions.size()];
		int incr = 0;
		for (String city : cityOptions) {
			cities[incr] = city;
			incr++;
		}
		JComboBox<String> options = new JComboBox<String>(cities);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chosenCity = options.getSelectedItem().toString();
				confirmCity(evt, options, chosenCity);
			}
		});
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);

	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options, String chosenCity) {	
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to drive", "Are you sure you want to drive", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.driveDestinationName = chosenCity;
			board.actionName = "Drive";
			gameSetup.oneTurn();
			gui.removePanel(panel);
			gui.updateImage();
		} else {

		}
	}

}
