package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;
import Player.Player;

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
		String currentCity = board.currentPlayer.location.cityName;
		ArrayList<Player> players = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < board.currentPlayers.size(); i++) {
			if (board.currentPlayers.get(i).location.cityName.equals(currentCity)) {
				if (!board.currentPlayer.equals(board.currentPlayers.get(i))) {
					players.add(board.currentPlayers.get(i));
					count++;
				}
			}
		}
		if (count <= 0) {
			JOptionPane.showMessageDialog(null, "There are no other Players on this city");
			return;
		} else {
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to give (yes) or take (no)",
					"Do you want to give (yes) or take (no)", JOptionPane.YES_NO_OPTION);
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
					player.addActionListener(new ActionListener(){
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

	}

	protected void chooseCard() {
		if(action){
			String[] options = new String[board.currentPlayer.hand.size()];
			int count = 0;
			for(String i:board.currentPlayer.hand.keySet()){
				options[count] = i;
				count++;
			}
			String[] concatColorOptions = board.colorConcator.concatColor(options, board.cities);
			JComboBox<String> list = new JComboBox<String>(concatColorOptions);
			panel.add(list);
			gui.addPanel(panel, BorderLayout.CENTER);
			list.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String card = (list.getSelectedItem().toString().split(" "))[0];
					board.actionName = Board.ActionName.SHAREKNOWLEDGE;
					board.playerToShare = board.currentPlayers.get(playerNumber);
					board.isGiving = action;
					board.cityToShare = board.currentPlayer.hand.get(card);
					gameSetup.oneTurn();
					gui.removePanel(panel);
					gui.updateImage();
				}
				
			});
		}
		else{
			String[] options = new String[board.currentPlayers.get(playerNumber).hand.size()];
			int count = 0;
			for(String i:board.currentPlayers.get(playerNumber).hand.keySet()){
				options[count] = i;
				count++;
			}
			String[] concatColorOptions = board.colorConcator.concatColor(options, board.cities);
			JComboBox<String> list = new JComboBox<String>(concatColorOptions);
			panel.add(list);
			gui.addPanel(panel, BorderLayout.CENTER);
			list.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String card = (list.getSelectedItem().toString().split(" "))[0];
					board.actionName = Board.ActionName.SHAREKNOWLEDGE;
					board.playerToShare = board.currentPlayers.get(playerNumber);
					board.isGiving = action;
					board.cityToShare = board.currentPlayers.get(playerNumber).hand.get(card);
					gameSetup.oneTurn();
					gui.removePanel(panel);
					gui.updateImage();
				}
				
			});
			
		}
		
	}

	protected void setPlayernumber(int parseInt) {
		playerNumber = parseInt-1;
	}

}
