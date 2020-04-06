package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import game.GameColor;
import gui.GameGUI;
import initialize.GameSetup;

public class TreatDiseaseListener implements ActionListener {

	Board board;
	private GameSetup gameSetup;
	private GameGUI gui;
	private JPanel panel;

	public TreatDiseaseListener(Board board, GameGUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (board.dispatcherCase == -1) {
			// TODO broken
			Set<GameColor> colors = null;// board.currentPlayer.playerData.location.getExistingDiseases();
			if (colors.isEmpty()) {
				return;
			}
			// TODO broken right now
			String[] colorOptions = colors.toArray(new String[colors.size()]);

			JComboBox<String> options = new JComboBox<String>(colorOptions);
			options.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					confirmRemoveDisease(options);
				}
			});
			panel = new JPanel();
			panel.add(options);
			// gui.addPanel(panel, BorderLayout.CENTER);
		} else {
			JOptionPane.showMessageDialog(null, board.messages.getString("dispatcherErrorMessage"));
		}

	}

	protected void confirmRemoveDisease(JComboBox<String> options) {
		String chosenCity = options.getSelectedItem().toString();
		if (chosenCity.equals(board.messages.getString("cancel"))) {
			// gui.removePanel(panel);
			return;
		}
		int choice = JOptionPane.showConfirmDialog(null, board.messages.getString("treatConfirmation"),
				board.messages.getString("treatConfirmation"), JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.diseaseBeingTreated = chosenCity;
			board.actionName = Board.ActionName.TREATDISEASE;
			// gameSetup.oneTurn();
			// gui.removePanel(panel);
			// gui.updateImage();
		} else {

		}

	}
}
