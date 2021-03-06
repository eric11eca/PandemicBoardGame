package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import gui.view.UITurn;
import lang.I18n;
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
	private UI popupUI;
	private JFrame popupFrame;
//	private UIDeck playerDeckUI;
//	private UIDeck playerDiscardUI;
//	private UIDeck infectDeckUI;
//	private UIDeck infectDiscardUI;
//	private UIAction actionUI;
	private UIBoard boardUI;
//	private UIDisease diseaseUI;
//	private UIInfectionRate infectionUI;
//	private UIOutbreak outbreakUI;
//	private UIPlayer[] playerUIs;

	private JPanel middlePanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private GameState game;

	public GameGUI(GameState game) {
		loadFrame();
		this.game = game;

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

	public void initBoardPanel(Render render, TurnController turnController) {
		boardUI = new UIBoard(render, turnController);
		uis.add(boardUI);
		middlePanel.add(boardUI);
	}

	public void initDeckPanel(Deck playerDeck, Deck playerDiscard, Deck infectDeck, Deck infectDiscard) {
		UIDeck playerDeckUI = new UIDeck(I18n.format("deck.player"), Color.YELLOW, playerDeck::size);
		UIDeck playerDiscardUI = new UIDeck(I18n.format("deck.player_discard"), Color.YELLOW, playerDiscard::size);
		UIDeck infectDeckUI = new UIDeck(I18n.format("deck.infect"), Color.GREEN, infectDeck::size);
		UIDeck infectDiscardUI = new UIDeck(I18n.format("deck.infect_discard"), Color.GREEN, infectDiscard::size);
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

	public void initStatusPanel(Set<GameColor> curedDiseaseSet, GameCubePool gameCubePool, Render render) {
		JPanel statusPanel = new JPanel();
		// statusPanel.setPreferredSize(new Dimension(200, 100));
		statusPanel.setLayout(new BorderLayout());
		UIOutbreak outbreakUI = new UIOutbreak(game::getOutbreakLevel);
		statusPanel.add(outbreakUI, BorderLayout.NORTH);
		uis.add(outbreakUI);
		UIDisease diseaseUI = new UIDisease(render, curedDiseaseSet::contains, gameCubePool::isDiseaseEradicated,
				gameCubePool::getDiseaseCubeCount);
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
		UITurn uiTurn = new UITurn(turnController);
		bottomPanel.add(uiTurn, BorderLayout.NORTH);
		uis.add(uiTurn);
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
		if (popupUI != null)
			popupUI.update();
		if (game.isLost()) {
			JOptionPane.showMessageDialog(null, "You lost");
		}
		if (game.isWon()) {
			JOptionPane.showMessageDialog(null, "You win");
		}
	}

	public void displayPopup(String title, UI message) {
		popupUI = message;
		if (popupFrame != null)
			popupFrame.dispose();
		popupFrame = new JFrame();
		popupFrame.setTitle(title);
		JComponent component = (JComponent) message;
		popupFrame.add(component);
		popupFrame.pack();
		popupFrame.setLocationRelativeTo(frame);
		popupFrame.setResizable(false);
		// popupFrame.setm
		popupFrame.setVisible(true);
		// boardUI.displayMessage((JComponent) message);
		update();
	}

	public void hidePopup() {
		if (popupFrame != null)
			popupFrame.dispose();
		update();
	}

	public JFrame getGameFrame() {
		return frame;
	}

}
