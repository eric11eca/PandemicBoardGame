package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import initialize.GameSetup;
import panel.GUI;

public class DriveListener implements ActionListener {
	String chosenCity;
	
	private JPanel panel;
	private Board board;
	private GUI gui;
	private GameSetup gameSetup;

	public DriveListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Set<String> cityOptions = board.currentPlayer.playerData.location.neighbors.keySet();
		String[] cities = new String[cityOptions.size() + 1];
		int i = 0;
		for (String city : cityOptions) {
			cities[i] = city;
			i++;
		}
		String[] concatColorOptions = board.colorConcator.concatColor(cities, board.cities);
		concatColorOptions[i] =  board.messages.getString("cancel"); 
		JComboBox<String> options = new JComboBox<String>(concatColorOptions);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				chosenCity = (options.getSelectedItem().toString()
						.split(board.messages.getString("lineConnector")))[0]; 
				confirmCity(options, chosenCity);
			}
		});
		
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);

	}

	protected void confirmCity(JComboBox<String> options, String chosenCity) {	
		
		if(chosenCity.equals( board.messages.getString("cancel"))){ 
			 gui.removePanel(panel);
			 return;
		 }
		int choice = JOptionPane.showConfirmDialog(null, 
				 board.messages.getString("driveConfirm"), 
				 board.messages.getString("Drive"), 
				JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.driveDestinationName = chosenCity;
			board.actionName = Board.ActionName.DRIVE;
			gameSetup.oneTurn();
			gui.removePanel(panel);
			gui.updateImage();
		} else {

		}
	}

}
