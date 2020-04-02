package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import game.Game;
import render.RenderDeck;

@SuppressWarnings("serial")
public class DeckUI extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		RenderDeck render = new RenderDeck(g2d);
		render.drawDeck(10, 0, Game.getInstance().getInfectionDeckSize(), "Infection:", Color.GREEN);
		render.drawDeck(90, 0, Game.getInstance().getInfectionDiscardSize(), "Discard:", Color.GREEN);
		render.drawDeck(170, 0, Game.getInstance().getPlayerDeckSize(), "Player Deck:", Color.GREEN);
		render.drawDeck(250, 0, Game.getInstance().getPlayerDiscardSize(), "Player Discard:", Color.GREEN);
		g2d.dispose();

	}

}
