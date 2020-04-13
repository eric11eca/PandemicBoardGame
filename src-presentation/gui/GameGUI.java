package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import game.player.PlayerController;
import gui.view.UIDeck;
import gui.view.UIDisease;
import gui.view.UIOutbreak;
import gui.view.UIPlayer;

public class GameGUI {
	private JFrame frame;
	private JPanel panelTop;
	private JPanel panelMiddle;
	private JPanel panelBottom;

	private LogoUI logoUI;
	private UIOutbreak outbreakUI;
	private UIDisease diseaseUI;
	private BoardUI boardUI;

	private UIDeck playerDeckUI;
	private UIDeck playerDiscardUI;
	private UIDeck infectDeckUI;
	private UIDeck infectDiscardUI;

	private JLayeredPane mainPane;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private static final Integer LAYER_UI = new Integer(1);

	public GameGUI(LogoUI logoUI, UIOutbreak outbreakUI, UIDisease diseaseUI, BoardUI boardUI) {
		mainPane = new JLayeredPane();
		this.logoUI = logoUI;
		this.outbreakUI = outbreakUI;
		this.diseaseUI = diseaseUI;

		this.boardUI = boardUI;

		loadFrame();
		loadTopPanel();
		loadMiddlePanel();
		loadBottomPanel();

		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	public void initDeckPanel(Deck<Card> playerDeck, Deck<Card> playerDiscard, Deck<CardCity> infectDeck,
			Deck<CardCity> infectDiscard) {
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
		for (int i = 0; i < players.length; i++) {
			UIPlayer ui = new UIPlayer(i + 1, players[i]::getPlayerCity, players[i]::getPlayerHandSize);
			topPanel.add(ui);
		}
	}

	public void showGUI() {
		frame.setVisible(true);
	}

	private void loadFrame() {
		frame = new JFrame();
		frame.setTitle("PANDEMIC");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
	}

	private void loadTopPanel() {
		panelTop = new JPanel();
		panelTop.setPreferredSize(new Dimension(1000, 120));
		panelTop.setLayout(new BorderLayout());

		logoUI.setPreferredSize(new Dimension(200, 120));
		outbreakUI.setPreferredSize(new Dimension(500, 50));
		JPanel mid = new JPanel();
		mid.setLayout(new BorderLayout());
		mid.add(outbreakUI, BorderLayout.NORTH);
		mid.add(diseaseUI, BorderLayout.CENTER);

		panelTop.add(logoUI, BorderLayout.WEST);
		panelTop.add(mid, BorderLayout.CENTER);

		frame.add(panelTop, BorderLayout.NORTH);

	}

	private void loadBottomPanel() {

	}

	private void loadMiddlePanel() {
		panelMiddle = new JPanel();
		panelMiddle.setPreferredSize(new Dimension(1000, 700));
		panelMiddle.setLayout(new BorderLayout());

		panelMiddle.add(boardUI);
		frame.add(panelMiddle, BorderLayout.CENTER);

	}
}
