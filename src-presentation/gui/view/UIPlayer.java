package gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.city.City;
import render.RenderPlayer;

@SuppressWarnings("serial")
public class UIPlayer extends JPanel implements UI {

	private RenderPlayer renderPlayer;

	public UIPlayer(RenderPlayer renderPlayer) {
		this.renderPlayer = renderPlayer;
		// this.setPreferredSize(new Dimension(200, 100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderPlayer.renderPlayerInfoBoard((Graphics2D) g);
//		Graphics2D g2d = (Graphics2D) g.create();
//		int ascent = g2d.getFontMetrics().getAscent();
//		int height = g2d.getFontMetrics().getHeight();
//		g2d.drawString("Player " + playerNumber, 0, ascent);
//		g2d.drawString(location.get().getName(), 0, ascent + height);
//		g2d.drawString(String.valueOf(handSize.get()), 0, ascent + height * 2);
//		g2d.dispose();
	}

	@Override
	public void update() {
		repaint();
	}
}
