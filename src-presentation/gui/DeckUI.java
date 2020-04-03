package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import game.Game;
import game.cards.Card;
import game.cards.CardCity;
import game.cards.Deck;
import render.RenderDeck;

@SuppressWarnings("serial")
public class DeckUI extends JComponent {
	private Deck<CardCity> infectionDeck;
	private Deck<CardCity> infectionDiscard;
	private Deck<Card> playerDeck;
	private Deck<Card> playerDiscard;

	public DeckUI(Deck<CardCity> infectionDeck, Deck<CardCity> infectionDiscard, Deck<Card> playerDeck,
			Deck<Card> playerDiscard) {
		super();
		this.infectionDeck = infectionDeck;
		this.infectionDiscard = infectionDiscard;
		this.playerDeck = playerDeck;
		this.playerDiscard = playerDiscard;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		RenderDeck render = new RenderDeck(g2d);
		render.drawDeck(10, 0, infectionDeck.size(), "Infection:", Color.GREEN);
		render.drawDeck(90, 0, infectionDiscard.size(), "Discard:", Color.GREEN);
		render.drawDeck(170, 0, playerDeck.size(), "Player Deck:", Color.ORANGE);
		render.drawDeck(250, 0, playerDiscard.size(), "Player Discard:", Color.ORANGE);
		g2d.dispose();

	}

}
