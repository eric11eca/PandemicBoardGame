package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Initialize.Board;
import Panel.GUI;

public class ShareKnowledgeListener implements ActionListener {
	
	Board board;
	boolean action;
	
	public ShareKnowledgeListener(Board board, GUI gui){
		this.board=board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String currentCity = board.currentPlayer.location.cityName;
		int count = 0;
		for(int i=0;i<board.currentPlayers.size();i++){
			System.out.println(board.currentPlayers.get(i).location);
			if(board.currentPlayers.get(i).location.cityName.equals(currentCity)){
				count++;
			}
		}
		if(count <=1){
			JOptionPane.showMessageDialog(null, 
					"There are no other Players on this city");
			return;
		}
		else{
			int choice = JOptionPane.showConfirmDialog(null, "Do you want to give (yes) or take (no)", "Do you want to give (yes) or take (no)", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				action = true;
			}
			else{
				action = false;
			}
		}
		
		

	}

}
