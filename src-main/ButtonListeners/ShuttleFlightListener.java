package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Initialize.Board;

public class ShuttleFlightListener implements ActionListener {

	Board board;

	public ShuttleFlightListener(Board board) {
		this.board = board;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] cityOptions = new String[5];
		JComboBox<String> options = new JComboBox<String>(cityOptions);
		options.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                confirmCity(evt,options);
            }
		});

	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options) {
		 String chosenCity = options.getSelectedItem().toString();
		 //Make the buttons and call the methods
		
	}
}
