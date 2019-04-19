package ButtonListeners;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Initialize.Board;
import Panel.GUI;

public class DriveListener implements ActionListener {
	private JPanel panel;
	private Board board;
	private GUI gui;

	public DriveListener(Board board, GUI gui) {
		this.board = board;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("drive");
		Set<String> cityOptions = board.currentPlayer.location.neighbors.keySet();
		String[] cities = new String[cityOptions.size()];
		int incr = 0;
		for (String city : cityOptions) {
			cities[incr] = city;
			incr++;
		}
		JComboBox<String> options = new JComboBox<String>(cities);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				confirmCity(evt, options);
			}
		});
		panel = new JPanel();
		panel.add(options);
		gui.addPanel(panel, BorderLayout.CENTER);

	}

	protected void confirmCity(ActionEvent evt, JComboBox<String> options) {
		String chosenCity = options.getSelectedItem().toString();
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to drive", "Are you sure you want to drive", JOptionPane.YES_NO_OPTION);
		if (choice == 0) {
			board.driveDestinationName = chosenCity;
			// drive to city
			gui.removePanel(panel);
		} else {

		}
	}

}
