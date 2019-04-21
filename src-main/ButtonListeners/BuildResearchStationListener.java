package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class BuildResearchStationListener implements ActionListener {
	
	Board board;
	GameSetup gameSetup;
	GUI gui;
	
	public BuildResearchStationListener(Board board, GUI gui, GameSetup gameSetup){
		this.board=board;
		this.gameSetup=gameSetup;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null,"Are you sure", "Are you sure", JOptionPane.YES_NO_OPTION);
				if(choice == 0 ){
					board.actionName = "BuildResearch";
					gameSetup.oneTurn();
					gui.updateImage();
				}
	}

}
