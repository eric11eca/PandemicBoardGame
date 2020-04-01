package buttonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Board;
import data.City;
import gui.CityChooser;
import gui.GUI;
import initialize.GameSetup;

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
		HashSet<City> cities = new HashSet<>();
		cities.addAll(board.currentPlayer.playerData.location.neighbors.values());

//		Set<String> cityOptions = board.currentPlayer.playerData.location.neighbors.keySet();
//		String[] cities = new String[cityOptions.size() + 1];
//		int i = 0;
//		for (String city : cityOptions) {
//			cities[i] = city;
//			i++;
//		}
//		String[] concatColorOptions = board.colorConcator.concatColor(cities, board.cities);
//		concatColorOptions[i] =  board.messages.getString("cancel"); 
//		JComboBox<String> options = new JComboBox<String>(concatColorOptions);
//		options.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent evt) {
//				chosenCity = (options.getSelectedItem().toString()
//						.split(board.messages.getString("lineConnector")))[0]; 
//				confirmCity(options, chosenCity);
//			}
//		});
//		
//		panel = new JPanel();
//		panel.add(options);
//		gui.addPanel(panel, BorderLayout.CENTER);

		CityChooser cityChooser = new CityChooser(cities, null, board.messages.getString("Drive"));
		cityChooser.letUserChooseACity().ifPresent(this::cityChosen);
	}

	private void cityChosen(City chosenCity) {
		board.driveDestination = chosenCity;
		board.actionName = Board.ActionName.DRIVE;
		gameSetup.oneTurn();
		gui.updateImage();
	}

//	protected void confirmCity(JComboBox<String> options, String chosenCity) {	
//		
//		if(chosenCity.equals(board.messages.getString("cancel"))){ 
//			 gui.removePanel(panel);
//			 return;
//		 }
//		int choice = JOptionPane.showConfirmDialog(null, 
//				 board.messages.getString("driveConfirm"), 
//				 board.messages.getString("Drive"), 
//				JOptionPane.YES_NO_OPTION);
//		if (choice == 0) {
//			board.driveDestinationName = chosenCity;
//			board.actionName = Board.ActionName.DRIVE;
//			gameSetup.oneTurn();
//			gui.updateImage();
//		} else {
//
//		}
//	}

}
