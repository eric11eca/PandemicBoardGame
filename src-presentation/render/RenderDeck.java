package render;

import java.awt.Color;
import java.awt.Graphics2D;

public class RenderDeck {
	private final int DECK_WIDTH = 70;
	private final int DECK_HEIGHT = 100;
	private Graphics2D g;

	public RenderDeck(Graphics2D g) {
		this.g = g;
	}

	public void drawDeck(int x, int y, int size, String name, Color color) {
		g.setColor(color);
		g.fillRect(x, y, DECK_WIDTH, DECK_HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString(name, x, y + DECK_HEIGHT / 2);
		g.drawString(String.valueOf(size), x, y + DECK_HEIGHT / 2 + g.getFontMetrics().getHeight());
	}
}
