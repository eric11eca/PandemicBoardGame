package main;

import java.io.IOException;

import javax.swing.SwingUtilities;

import gui.GameGUI;
import initialize.InitializationFacade;

public class Main {

	public static void main(String[] args) throws IOException {
		final int playerCount = 2;
		final int epidemic = 4;
		InitializationFacade init = new InitializationFacade(playerCount, epidemic);
		init.startGame();

		SwingUtilities.invokeLater(() -> {
			GameGUI gui = init.createGUI();
			gui.showGUI();
			gui.update();
		});

	}

}
