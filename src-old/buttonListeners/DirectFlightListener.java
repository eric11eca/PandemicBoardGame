package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import gui.GameGUI;
import initialize.GameSetup;

public class DirectFlightListener implements ActionListener {
	private Board board;
	private JPanel panel;
	private GameSetup gameSetup;
	private GameGUI gui;

	public DirectFlightListener(Board board, GameGUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> cityOptions = new ArrayList<>();

		for (String i : board.currentPlayer.playerData.hand.keySet()) {
			if (!i.equals(board.currentPlayer.playerData.location.getName())
					&& board.currentPlayer.playerData.hand.get(i).cardType.equals(Board.CardType.CITYCARD)) {
				cityOptions.add(i);
			}
		}
		if (cityOptions.size() == 0) {
			return;
		}
		cityOptions.add(board.messages.getString("cancel"));
		String[] cityOptionsArray = cityOptions.toArray(new String[cityOptions.size()]);
		String[] concatColorOptions = board.colorConcator.concatColor(cityOptionsArray, board.cities);
		JComboBox<String> options = new JComboBox<String>(concatColorOptions);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirmCity(options);
			}
		});
		panel = new JPanel();
		panel.add(options);
		// gui.addPanel(panel, BorderLayout.CENTER);
	}

	protected void confirmCity(JComboBox<String> options) {
		String chosenCity = (options.getSelectedItem().toString().split(board.messages.getString("lineConnector")))[0];
		if (chosenCity.equals(board.messages.getString("cancel"))) {
			// gui.removePanel(panel);
			return;
		}
		int choice = JOptionPane.showConfirmDialog(null, board.messages.getString("flyConfirmation"),
				board.messages.getString("directFlight"), JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.cityCardNameDirect = chosenCity;
			board.actionName = Board.ActionName.DIRECTFLIGHT;
			// gameSetup.oneTurn();
			// gui.removePanel(panel);
			// gui.updateImage();
		}
	}
}
