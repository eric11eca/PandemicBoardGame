package main;

import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import gui.GameGUI;
import initialize.InitializationFacade;

public class Main {

	public static void main(String[] args) {
		StartGUI startGUI = new StartGUI(Main::startGame);
		startGUI.setVisible(true);

	}

	private static void startGame(int playerCount, int epidemic) {
		InitializationFacade init;
		try {
			init = new InitializationFacade(playerCount, epidemic);
			SwingUtilities.invokeLater(() -> {
				GameGUI gui = init.createGUI();
				gui.showGUI();
				gui.update();
			});
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

}
