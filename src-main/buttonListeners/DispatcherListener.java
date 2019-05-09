package buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import initialize.Board;
import panel.GUI;

public class DispatcherListener implements ActionListener {

	private Board board;
	private GUI gui;
	private JPanel panel;

	public DispatcherListener(Board board, GUI gui) {
		this.board=board;
		this.gui=gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		JButton moveToCity = new JButton("Move to another player");
		JButton moveAsSelf = new JButton("Move as though you're own");

	}

}
