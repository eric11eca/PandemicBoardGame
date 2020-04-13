package gui.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.Supplier;

import javax.swing.JComponent;

import game.city.City;

@SuppressWarnings("serial")
public class UIPlayer extends JComponent {

	private int playerNumber;
	private Supplier<City> location;
	private Supplier<Integer> handSize;

	public UIPlayer(int playerNumber, Supplier<City> location, Supplier<Integer> handSize) {
		this.playerNumber = playerNumber;
		this.location = location;
		this.handSize = handSize;
		this.setPreferredSize(new Dimension(200, 100));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int ascent = g2d.getFontMetrics().getAscent();
		int height = g2d.getFontMetrics().getHeight();
		g2d.drawString("Player " + playerNumber, 0, ascent);
		g2d.drawString(location.get().getName(), 0, ascent + height);
		g2d.drawString(String.valueOf(handSize.get()), 0, ascent + height * 2);
		g2d.dispose();
	}
}
