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

public class DirectFlightListener implements ActionListener {

	Board board;
	private JPanel panel;
	GameSetup gameSetup;
	private GUI gui;

	public DirectFlightListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> cityOptions = new ArrayList<>();

		for (String i : board.currentPlayer.playerData.hand.keySet()) {
			if (!i.equals(board.currentPlayer.playerData.location.cityName)
					&& board.currentPlayer.playerData.hand.get(i).cardType.equals(Board.CardType.CITYCARD)) {
				cityOptions.add(i);
			}
		}
		if (cityOptions.size() == 0) {
			return;
		}
		cityOptions.add("Cancel");
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
		gui.addPanel(panel, BorderLayout.CENTER);
	}

	protected void confirmCity(JComboBox<String> options) {
		String chosenCity = (options.getSelectedItem().toString().split(" "))[0];
		if (chosenCity.equals("Cancel")) {
			gui.removePanel(panel);
			return;
		}
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to fly", "Direct Flight",
				JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.cityCardNameDirect = chosenCity;
			board.actionName = Board.ActionName.DIRECTFLIGHT;
			gameSetup.oneTurn();
			gui.removePanel(panel);
			gui.updateImage();
		} else {

		}

	}

}
