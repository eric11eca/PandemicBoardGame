package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import initialize.GameSetup;
import panel.GUI;
import player.Player;

public class ShareKnowledgeListener implements ActionListener {
	Board board;
	boolean action;
	JPanel panel;
	GUI gui;
	int playerNumber;
	GameSetup gameSetup;
	
	public ShareKnowledgeListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (board.dispatcherCase == -1) {
			String currentCity = board.currentPlayer.playerData.location.cityName;
			ArrayList<Player> players = new ArrayList<>();
			int count = 0;
			for (int i = 0; i < board.currentPlayers.size(); i++) {
				if (board.currentPlayers.get(i).playerData.location.cityName.equals(currentCity)) {
					if (!board.currentPlayer.equals(board.currentPlayers.get(i))) {
						players.add(board.currentPlayers.get(i));
						count++;
					}
				}
			}
			if (count <= 0) {
				JOptionPane.showMessageDialog(null, 
						board.messages.getString("noPlayerInCityErrorMessage")); 
				return;
			} else {
				int choice = JOptionPane.showConfirmDialog(null, 
						board.messages.getString("shareKnowledgePrompt"), 
						board.messages.getString("shareKnowledgePrompt"), 
						JOptionPane.YES_NO_OPTION);
				if (choice == 0) {
					action = true;
				} else {
					action = false;
				}
			}
			panel = new JPanel();

			for (int i = 0; i < players.size(); i++) {
				for (int j = 0; j < board.currentPlayers.size(); j++) {
					if (board.currentPlayers.get(j).equals(players.get(i))) {
						JButton player = new JButton(Integer.toString(j + 1));
						player.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								setPlayernumber(Integer.parseInt(player.getText()));
								gui.removePanel(panel);
								panel = new JPanel();
								chooseCard();
							}
						});
						panel.add(player);
					}

				}
			}
			gui.addPanel(panel, BorderLayout.CENTER);
		} else {
			JOptionPane.showMessageDialog(null, 
					board.messages.getString("dispatcherErrorMessage")); 
		}

	}

	protected void chooseCard() {
		if (action) {
			String[] options;
			if (board.currentPlayer.playerData.role == Board.Roles.RESEARCHER) {
				System.out.println("Researcher");
				options = new String[board.currentPlayer.playerData.hand.size()];
				int count = 0;
				for (String i : board.currentPlayer.playerData.hand.keySet()) {
					options[count] = i;
					count++;
				}
			} else {
				System.out.println("Not Researcher");
				options = new String[1];
				for (String i : board.currentPlayers.get(playerNumber).playerData.hand.keySet()) {
					if (i.equals(board.currentPlayer.playerData.location.cityName)) {
						options[0] = i;
						break;
					}
					if(options[0]==null){
						JOptionPane.showMessageDialog(null,board.messages.getString("noCityCard"));
						return;
					}
				}
			}
			String[] concatColorOptions = board.colorConcator.concatColor(options, board.cities);
			JComboBox<String> list = new JComboBox<String>(concatColorOptions);
			panel.add(list);
			gui.addPanel(panel, BorderLayout.CENTER);
			list.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String card = (list.getSelectedItem().toString()
							.split(board.messages.getString("lineConnector")))[0]; 
					board.actionName = Board.ActionName.SHAREKNOWLEDGE;
					board.playerToShare = board.currentPlayers.get(playerNumber);
					board.isGiving = action;
					board.cityToShare = board.currentPlayer.playerData.hand.get(card);
					gameSetup.oneTurn();
					gui.removePanel(panel);
					gui.updateImage();
				}

			});
		} else {
			String[] options;
			if (board.currentPlayers.get(playerNumber).playerData.role == Board.Roles.RESEARCHER) {
				System.out.println("Researcher 1");
				options = new String[board.currentPlayers.get(playerNumber).playerData.hand.size()];
				int count = 0;
				for (String i : board.currentPlayers.get(playerNumber).playerData.hand.keySet()) {
					options[count] = i;
					count++;
				}
			} else {
				System.out.println("Not Researcher 1");
				options = new String[1];
				for (String i : board.currentPlayers.get(playerNumber).playerData.hand.keySet()) {
					if (i.equals(board.currentPlayer.playerData.location.cityName)) {
						options[0] = i;
						break;
					}
					if(options[0]==null){
						JOptionPane.showMessageDialog(null,board.messages.getString("noCityCard"));
						return;
					}
				}
			}
			String[] concatColorOptions = board.colorConcator.concatColor(options, board.cities);
			JComboBox<String> list = new JComboBox<String>(concatColorOptions);
			panel.add(list);
			gui.addPanel(panel, BorderLayout.CENTER);
			list.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String card = (list.getSelectedItem().
							toString().split(board.messages.getString("lineConnector")))[0]; 
					board.actionName = Board.ActionName.SHAREKNOWLEDGE;
					board.playerToShare = board.currentPlayers.get(playerNumber);
					board.isGiving = action;
					board.cityToShare = board.currentPlayers.get(playerNumber).playerData.hand.get(card);
					gameSetup.oneTurn();
					gui.removePanel(panel);
					gui.updateImage();
				}

			});

		}

	}

	protected void setPlayernumber(int parseInt) {
		playerNumber = parseInt - 1;
	}

}
