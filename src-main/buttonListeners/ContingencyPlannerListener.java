package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import panel.GUI;

public class ContingencyPlannerListener implements ActionListener {

	private Board board;
	JPanel panel;
	GUI gui;

	public ContingencyPlannerListener(Board board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] cards = board.discardEventCards.toArray(new String[board.discardEventCards.size()]);
		JComboBox<String> cardList = new JComboBox<String>(cards);

		cardList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (board.currentPlayer.playerData.specialEventCard == null) {
					JOptionPane.showMessageDialog(null,
							"Contingency Planner already has a event card on his role card");
				} else {
					board.currentPlayer.playerData.specialEventCard = cardList.getSelectedItem().toString();
					gui.removePanel(panel);
					gui.updateImage();
				}
			}

		});

		panel = new JPanel();
		panel.add(cardList);
		gui.addPanel(panel, BorderLayout.CENTER);

	}

}
