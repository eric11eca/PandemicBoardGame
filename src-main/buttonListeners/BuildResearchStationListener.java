package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import initialize.Board;
import initialize.GameSetup;
import panel.GUI;

public class BuildResearchStationListener implements ActionListener {

	Board board;
	GameSetup gameSetup;
	GUI gui;
	JPanel panel;

	public BuildResearchStationListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gameSetup = gameSetup;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (board.dispatcherCase == -1) {
			int choice = JOptionPane.showConfirmDialog(null, "Are you sure", "Build Research Station",
					JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				if (board.currentResearchStation.size() == 6) {
					ArrayList<String> cityOptions = new ArrayList<>();

					for (String i : board.currentResearchStation.keySet()) {
						if (!i.equals(board.currentPlayer.playerData.location.cityName)) {
							cityOptions.add(i);
						}
					}
					if (cityOptions.size() == 0) {
						return;
					}
					cityOptions.add("Cancel");
					String[] cityOptionsArray = cityOptions.toArray(new String[cityOptions.size()]);
					JComboBox<String> options = new JComboBox<String>(cityOptionsArray);
					options.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							confirmCity(options);
						}
					});
					panel = new JPanel();
					panel.add(options);
					gui.addPanel(panel, BorderLayout.CENTER);

				} else {
					board.actionName = Board.ActionName.BUILDRESEARCH;
					gameSetup.oneTurn();
					gui.updateImage();
				}
			}
		} else{
			JOptionPane.showMessageDialog(null, "Cannot do this action as a dispatcher");
		}
	}

	protected void confirmCity(JComboBox<String> options) {
		String chosenCity = (options.getSelectedItem().toString().split(" "))[0];
		if (chosenCity.equals("Cancel")) {
			gui.removePanel(panel);
			return;
		}
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this research station",
				"Build Research Station", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.stationToRemove = chosenCity;
			board.actionName = Board.ActionName.BUILDRESEARCH;
			gameSetup.oneTurn();
			gui.removePanel(panel);
			gui.updateImage();
		}

	}

}
