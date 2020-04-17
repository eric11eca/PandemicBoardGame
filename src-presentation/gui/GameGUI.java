package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game.GameColor;
import game.GameState;
import game.TurnController;
import game.cards.Deck;
import game.disease.GameCubePool;
import game.player.PlayerController;
import gui.view.UI;
import gui.view.UIAction;
import gui.view.UIBoard;
import gui.view.UIDeck;
import gui.view.UIDisease;
import gui.view.UIInfectionRate;
import gui.view.UIOutbreak;
import gui.view.UIPlayer;
import render.Render;

public class GameGUI {
	private JFrame frame;
//	private JPanel panelTop;
//	private JPanel panelMiddle;
//	private JPanel panelBottom;

	// private LogoUI logoUI;
//	private UIOutbreak outbreakUI;
//	private UIDisease diseaseUI;
	// private UIBoard boardUI;
	private List<UI> uis;
//	private UIDeck playerDeckUI;
//	private UIDeck playerDiscardUI;
//	private UIDeck infectDeckUI;
//	private UIDeck infectDiscardUI;
//	private UIAction actionUI;
//	private UIBoard boardUI;
//	private UIDisease diseaseUI;
//	private UIInfectionRate infectionUI;
//	private UIOutbreak outbreakUI;
//	private UIPlayer[] playerUIs;

	private JPanel middlePanel;
	private JPanel topPanel;
	private JPanel bottomPanel;

	public GameGUI() {
		loadFrame();

		middlePanel = new JPanel(new BorderLayout());
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		JPanel uiPanel = new JPanel(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(800, 100));
		middlePanel.setPreferredSize(new Dimension(1000, 400));
		bottomPanel.setPreferredSize(new Dimension(800, 100));
		uiPanel.add(topPanel, BorderLayout.NORTH);
		uiPanel.add(bottomPanel, BorderLayout.SOUTH);
		uiPanel.add(middlePanel, BorderLayout.CENTER);
		frame.add(uiPanel);

		// mainPane.setPreferredSize(new Dimension(800, 600));

		// frame.add(mainPane, BorderLayout.CENTER);

		uis = new ArrayList<>();

	}

	public void initBoardPanel(Render render) {
		UIBoard board = new UIBoard(render);
		uis.add(board);
		middlePanel.add(board);
	}

	public void initDeckPanel(Deck playerDeck, Deck playerDiscard, Deck infectDeck, Deck infectDiscard) {
		UIDeck playerDeckUI = new UIDeck("Player Deck", Color.YELLOW, playerDeck::size);
		UIDeck playerDiscardUI = new UIDeck("Player Discard", Color.YELLOW, playerDiscard::size);
		UIDeck infectDeckUI = new UIDeck("Infect Deck", Color.GREEN, infectDeck::size);
		UIDeck infectDiscardUI = new UIDeck("Infect Discard", Color.GREEN, infectDiscard::size);
		JPanel deckPanel = new JPanel();
		deckPanel.add(playerDeckUI);
		deckPanel.add(playerDiscardUI);
		deckPanel.add(infectDeckUI);
		deckPanel.add(infectDiscardUI);
		uis.add(playerDeckUI);
		uis.add(playerDiscardUI);
		uis.add(infectDeckUI);
		uis.add(infectDiscardUI);
		// deckPanel.setPreferredSize(new Dimension(800, 100));
		bottomPanel.add(deckPanel, BorderLayout.EAST);
	}

	public void initPlayerPanel(Render render, PlayerController[] playerControllers, TurnController turnController) {
		JPanel playerPanel = new JPanel();
		playerPanel.setLayout(new GridLayout(1, playerControllers.length));
		for (int i = 0; i < playerControllers.length; i++) {
			UIPlayer ui = new UIPlayer(this, render, playerControllers[i], turnController);
			playerPanel.add(ui);
			uis.add(ui);
		}
		topPanel.add(playerPanel, BorderLayout.CENTER);
	}

	public void initStatusPanel(GameState game, Set<GameColor> curedDiseaseSet, GameCubePool gameCubePool,
			Render render) {
		JPanel statusPanel = new JPanel();
		// statusPanel.setPreferredSize(new Dimension(200, 100));
		statusPanel.setLayout(new BorderLayout());
		UIOutbreak outbreakUI = new UIOutbreak(game::getOutbreakLevel);
		statusPanel.add(outbreakUI, BorderLayout.NORTH);
		uis.add(outbreakUI);
		UIDisease diseaseUI = new UIDisease(render, curedDiseaseSet::contains, gameCubePool::isDiseaseEradicated);
		statusPanel.add(diseaseUI, BorderLayout.CENTER);
		uis.add(diseaseUI);
		UIInfectionRate infectionUI = new UIInfectionRate(game::getInfectionRateIndex);
		statusPanel.add(infectionUI, BorderLayout.SOUTH);
		uis.add(infectionUI);
		topPanel.add(statusPanel, BorderLayout.EAST);
	}

	public void initActionPanel(TurnController turnController) {
		UIAction ui = new UIAction(this, turnController);
		bottomPanel.add(ui, BorderLayout.CENTER);
		uis.add(ui);
	}

	public void showGUI() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void loadFrame() {
		frame = new JFrame();
		frame.setTitle("PANDEMIC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	public void update() {
		uis.forEach(UI::update);
	}

	public void displayMessage(String message) {
		MessageUI ui = new MessageUI(message);
	}

	public void hideMessage() {

	}

}
