package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class BuildResearchStationListener implements ActionListener {
	
	Board board;
	
	GameSetup gameSetup;
	GUI gui;
	JPanel panel;

	public BuildResearchStationListener(Board board, GUI gui, GameSetup gameSetup) {
		this.board = board;
		this.gameSetup = gameSetup;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure", "Are you sure", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			if (board.currentResearchStation.size() == 6) {
				ArrayList<String> cityOptions = new ArrayList<>();
				
				for(String i: board.currentResearchStation.keySet()){
					if(!i.equals(board.currentPlayer.location.cityName)){
						cityOptions.add(i);
					}
				}
				if(cityOptions.size() == 0){
					return;
				}
				cityOptions.add("Cancel");
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

			} else {
				board.actionName = "BuildResearch";
				gameSetup.oneTurn();
				gui.updateImage();
			}
		}
	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options) {
		 String chosenCity = options.getSelectedItem().toString();
		 if(chosenCity.equals("Cancel")){
			 gui.removePanel(panel);
			 return;
		 }
		 int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this research station", "Are you sure you want to remove this research station", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				board.stationToRemove= chosenCity;
				board.actionName = "BuilResearch";
				gameSetup.oneTurn();
				gui.removePanel(panel);
				gui.updateImage();
			}
		
	}

}
