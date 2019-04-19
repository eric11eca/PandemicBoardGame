package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Initialize.Board;
import Panel.GUI;

public class ShareKnowledgeListener implements ActionListener {
	
	Board board;
	
	public ShareKnowledgeListener(Board board, GUI gui){
		this.board=board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
