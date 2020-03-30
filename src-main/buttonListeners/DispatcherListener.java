package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import data.Board;
import panel.GUI;

public class DispatcherListener implements ActionListener {
	private Board board;
	private GUI gui;
	private JPanel panel;

	public DispatcherListener(Board board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton moveToCity = new JButton(board.messages.getString("playerMove")); 
		JButton moveAsSelf = new JButton(board.messages.getString("dispatcherMove.1")); 
		panel = new JPanel();
		panel.add(moveToCity);
		panel.add(moveAsSelf);
		gui.addPanel(panel, BorderLayout.CENTER);

		moveToCity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.removePanel(panel);
				String[] players = new String[board.currentPlayers.size() - 1];
				int count = 0;
				for (int i = 0; i < board.currentPlayers.size(); i++) {
					if (!board.currentPlayer.equals(board.currentPlayers.get(i))) {
						players[count] = Integer.toString(i + 1);
						count++;
					}
				}
				JComboBox<String> listOfPlayers = new JComboBox<String>(players);
				panel = new JPanel();
				panel.add(listOfPlayers);
				gui.addPanel(panel, BorderLayout.CENTER);

				listOfPlayers.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int playerNumber = Integer.parseInt(listOfPlayers.getSelectedItem().toString());
						gui.removePanel(panel);
						String[] cities = new String[board.currentPlayers.size() - 1];
						int count = 0;
						for (int i = 0; i < board.currentPlayers.size(); i++) {
							if (playerNumber != i) {
								cities[count] = board.currentPlayers.get(i).playerData.location.cityName;
								count++;
							}
						}
						JComboBox<String> listOfCities = new JComboBox<String>(cities);
						panel = new JPanel();
						panel.add(listOfCities);
						gui.addPanel(panel, BorderLayout.CENTER);

						listOfCities.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								board.pawnTobeMoved = playerNumber;
								board.newLocationName = listOfCities.getSelectedItem().toString();
								gui.removePanel(panel);
								gui.updateImage();
							}
						});
					}
				});
			}
		});

		moveAsSelf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.removePanel(panel);
				String[] players = new String[board.currentPlayers.size() - 1];
				int count = 0;
				for (int i = 0; i < board.currentPlayers.size(); i++) {
					if (!board.currentPlayer.equals(board.currentPlayers.get(i))) {
						players[count] = Integer.toString(i + 1);
						count++;
					}
				}
				JComboBox<String> list = new JComboBox<String>(players);
				panel = new JPanel();
				panel.add(list);
				gui.addPanel(panel, BorderLayout.CENTER);

				list.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						board.pawnTobeMoved = Integer.parseInt(list.getSelectedItem().toString()) - 1;
						board.dispatcherCase = 1;
						gui.removePanel(panel);
						gui.updateImage();
					}
				});
			}
		});
	}
}
