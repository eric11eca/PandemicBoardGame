package gui.interaction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import game.cards.Card;
import render.Render;
import render.RenderCard;

@SuppressWarnings("serial")
public class UICard extends JPanel {
	private Render render;
	private RenderCard renderCard;
	private Card card;
	private boolean isSelected;

	public UICard(Card card, Render render) {
		this.render = render;
		this.card = card;
		this.renderCard = new RenderCard();
		this.isSelected = false;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		renderCard.setRenderSize(g2d, getWidth(), getHeight());
		g2d.setColor(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
		renderCard.renderBackground(g2d);
		renderCard.renderCard(g2d, card, render);
		g2d.dispose();
	}
}
