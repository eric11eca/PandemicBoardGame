package main;

import java.io.IOException;

import initialize.InitializationFacade;

public class Main {

	public static void main(String[] args) throws IOException {
		final int playerCount = 2;
		final int epidemic = 4;
		InitializationFacade init = new InitializationFacade(playerCount, epidemic);
		init.createGUI().showGUI();
	}

}
