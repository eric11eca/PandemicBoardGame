package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game.GameColor;
import game.GameState;
import game.TurnController;
import game.cards.Deck;
import game.city.City;
import game.disease.GameCubePool;
import game.player.PlayerController;
import gui.view.UIAction;
import gui.view.UIBoard;
import gui.view.UIDeck;
import gui.view.UIDisease;
import gui.view.UIInfectionRate;
import gui.view.UIOutbreak;
import gui.view.UIPlayer;
import render.RenderCity;

public class GameGUI {
	private JFrame frame;
//	private JPanel panelTop;
//	private JPanel panelMiddle;
//	private JPanel panelBottom;

	// private LogoUI logoUI;
//	private UIOutbreak outbreakUI;
//	private UIDisease diseaseUI;
	// private UIBoard boardUI;

	private UIDeck playerDeckUI;
	private UIDeck playerDiscardUI;
	private UIDeck infectDeckUI;
	private UIDeck infectDiscardUI;

	private JLayeredPane mainPane;
	private JPanel middlePanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private static final Integer LAYER_UI = new Integer(1);
	private static final Integer LAYER_MESSAGE = new Integer(2);

	public GameGUI() {
		mainPane = new JLayeredPane();
		middlePanel = new JPanel(new BorderLayout());
		topPanel = new JPanel(new BorderLayout());
		bottomPanel = new JPanel(new BorderLayout());
		JPanel uiPanel = new JPanel(new BorderLayout());
		uiPanel.add(topPanel, BorderLayout.NORTH);
		uiPanel.add(bottomPanel, BorderLayout.SOUTH);
		uiPanel.add(middlePanel, BorderLayout.CENTER);
		mainPane.add(uiPanel, LAYER_UI);

		loadFrame();
		frame.add(mainPane);

	}

	public void initBoardPanel(Map<City, RenderCity> cityRenderers) {
		UIBoard board = new UIBoard(cityRenderers);
		middlePanel.add(board);
	}

	public void initDeckPanel(Deck playerDeck, Deck playerDiscard, Deck infectDeck, Deck infectDiscard) {
		playerDeckUI = new UIDeck("Player Deck", Color.YELLOW, playerDeck::size);
		playerDiscardUI = new UIDeck("Player Discard", Color.YELLOW, playerDiscard::size);
		infectDeckUI = new UIDeck("Infect Deck", Color.GREEN, infectDeck::size);
		infectDiscardUI = new UIDeck("Infect Discard", Color.GREEN, infectDiscard::size);
		JPanel deckPanel = new JPanel();
		deckPanel.add(playerDeckUI);
		deckPanel.add(playerDiscardUI);
		deckPanel.add(infectDeckUI);
		deckPanel.add(infectDiscardUI);
		deckPanel.setPreferredSize(new Dimension(800, 100));
		bottomPanel.add(deckPanel, BorderLayout.EAST);
	}

	public void initPlayerPanel(PlayerController[] players) {
		JPanel playerPanel = new JPanel();
		for (int i = 0; i < players.length; i++) {
			UIPlayer ui = new UIPlayer(i + 1, players[i]::getPlayerCity, players[i]::getPlayerHandSize);
			playerPanel.add(ui);
		}
		topPanel.add(playerPanel, BorderLayout.CENTER);
	}

	public void initStatusPanel(GameState game, Set<GameColor> curedDiseaseSet, GameCubePool gameCubePool) {
		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.add(new UIOutbreak(game::getOutbreakLevel), BorderLayout.NORTH);
		statusPanel.add(new UIDisease(curedDiseaseSet::contains, gameCubePool::isDiseaseEradicated),
				BorderLayout.CENTER);
		statusPanel.add(new UIInfectionRate(game::getInfectionRateIndex));
		topPanel.add(statusPanel, BorderLayout.EAST);
	}

	public void initActionPanel(TurnController turnController) {
		UIAction ui = new UIAction(turnController);
		bottomPanel.add(ui, BorderLayout.CENTER);
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

}
