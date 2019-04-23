package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class BuildResearchStationListener implements ActionListener {
	
	Board board;
	private GUI gui;
	private GameSetup gameSetup;
	
	public BuildResearchStationListener(Board board, GUI gui, GameSetup GameSetup){
		this.board=board;
		this.gui = gui;
		this.gameSetup = GameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null,"Are you sure", "Are you sure", JOptionPane.YES_NO_OPTION);
				if(choice == 0 ){
					//build research station
				}
	}

}
