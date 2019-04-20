package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Initialize.GameSetup;
import Panel.GUI;

public class CharterFlightListener implements ActionListener {
	
	private Board board;
	private JPanel panel;
	private GUI gui;
	private GameSetup gameSetup;
	
	public CharterFlightListener(Board board, GUI gui, GameSetup gameSetup){
		this.board=board;
		this.gui = gui;
		this.gameSetup = gameSetup;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("charter");
		String[] cityOptions = new String[47];
		int incr = 0;
		for(String i: board.cities.keySet()){
			if(!i.equals(board.currentPlayer.location.cityName)){
				cityOptions[incr] = i;
				incr++;
			}
		}
		JComboBox<String> options = new JComboBox<String>(cityOptions);
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
		 int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to fly", "Are you sure you want to fly", JOptionPane.YES_NO_OPTION);
			if (choice == 0) {
				board.driveDestinationName = chosenCity;
				board.actionName = "CharterFlight";
				gameSetup.oneTurn();
				gui.removePanel(panel);
				gui.updateImage();
			} else {

			}
		
	}

}
