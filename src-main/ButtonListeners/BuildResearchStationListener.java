package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Initialize.Board;
import Panel.GUI;

public class BuildResearchStationListener implements ActionListener {
	
	Board board;
	
	public BuildResearchStationListener(Board board, GUI gui){
		this.board=board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int choice = JOptionPane.showConfirmDialog(null,"Are you sure", "Are you sure", JOptionPane.YES_NO_OPTION);
				if(choice == 0 ){
					//build research station
				}
	}

}
