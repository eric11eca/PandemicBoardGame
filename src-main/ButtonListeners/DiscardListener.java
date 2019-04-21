package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import Initialize.Board;
import Panel.GUI;

public class DiscardListener implements ActionListener{
	
	private Board board;
	private GUI gui;
	private JCheckBox[] cardOptions;

	public DiscardListener(GUI gui, Board board, JCheckBox[] cardOptions){
		this.board=board;
		this.gui=gui;
		this.cardOptions=cardOptions;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int count=0;
		for(int i = 0;i<cardOptions.length;i++){
			if(cardOptions[i].isSelected())
				count++;
		}
		int cardNumberToDiscard = cardOptions.length-7;
		if(cardOptions.length-count!=7){
			JOptionPane.showMessageDialog(null, "Please only discard "+ cardNumberToDiscard+"cards");
		}
		else{
			
			for(int i = 0;i<cardOptions.length;i++){
				if(cardOptions[i].isSelected())
					board.currentPlayer.cardToBeDiscard.add(cardOptions[i].getText());
			}
			
		}
		
	}
	
	

}
