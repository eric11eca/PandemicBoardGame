package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Card.PlayerCard;
import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class FlightListener implements ActionListener {
	
	Board board;
	private JPanel panel;
	GameSetup gameSetup;
	private GUI gui;
	
	public FlightListener(Board board, GUI gui, GameSetup gameSetup){
		this.board=board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<String> cityOptions = new ArrayList<>();
		
		for(String i: board.currentPlayer.hand.keySet()){
			if(!i.equals(board.currentPlayer.location.cityName) && board.currentPlayer.hand.get(i).cardType.equals(Board.CardType.CITYCARD)){
				cityOptions.add(i);
			}
		}
		cityOptions.add("Cancel");
		
		if(cityOptions.size() == 0){
			return;
		}
		String[] cityOptionsArray = cityOptions.toArray(new String[cityOptions.size()]);
		JComboBox<String> options = new JComboBox<String>(cityOptionsArray);
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                confirmCity(evt,options);
            }
		});
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);
	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options) {
		 String chosenCity = options.getSelectedItem().toString();
		 if(chosenCity.equals("Cancel")){
			 gui.removePanel(panel);
			 return;
		 }
		 int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to fly", "Are you sure you want to fly", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				board.cityCardNameDirect= chosenCity;
				board.actionName = "DirectFlight";
				gameSetup.oneTurn();
				gui.removePanel(panel);
				gui.updateImage();
			} else {

			}
		
	}

}
