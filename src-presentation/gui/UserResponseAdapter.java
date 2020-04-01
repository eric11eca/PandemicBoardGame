package gui;

import javax.swing.JOptionPane;

public class UserResponseAdapter {
	private int choice;

	public UserResponseAdapter(int choice) {
		super();
		this.choice = choice;
	}

	public boolean userChoseOK() {
		return choice == JOptionPane.OK_OPTION;
	}

}
