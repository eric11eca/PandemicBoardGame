package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class ShuttleFlightListener implements ActionListener {

	Board board;
	private JPanel panel;
	GameSetup gameSetup;
	GUI gui;

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
		cityOptions.add("Cancel");
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
		String chosenCity = (options.getSelectedItem().toString().split(" "))[0];
		if (chosenCity.equals("Cancel")) {
			gui.removePanel(panel);
			return;
		} else {
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to take a shuttle",
					"Are you sure you want to take a shuttle", JOptionPane.YES_NO_OPTION);
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
