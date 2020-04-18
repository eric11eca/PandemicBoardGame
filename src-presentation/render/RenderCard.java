package render;

import java.awt.Graphics2D;

import game.cards.Card;
import game.city.City;

public class RenderCard {
	private static final int WIDTH = 250;
	private static final int HEIGHT = 50;
	private static final int CIRCLE_SIZE = 25;

	public void setRenderSize(Graphics2D g, int width, int height) {
		float scaleX = (float) width / WIDTH;
		float scaleY = (float) height / HEIGHT;
		float scale = Math.min(scaleX, scaleY);
		g.scale(scale, scale);
	}

	public void renderCard(Graphics2D g, Card card, Render render) {
		card.getCity().ifPresent(city -> renderCityCard(g, city, render));
	}

	public void renderBackground(Graphics2D g) {
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	private void renderCityCard(Graphics2D g, City city, Render render) {
		g.setColor(render.getRenderColor(city.getColor()));
		g.fillOval(0, (HEIGHT - CIRCLE_SIZE) / 2, CIRCLE_SIZE, CIRCLE_SIZE);
		int h = g.getFontMetrics().getHeight();
		g.setFont(g.getFont().deriveFont((float) HEIGHT / 2));
		g.drawString(city.getName(), CIRCLE_SIZE + 10, (HEIGHT - h) / 2 + g.getFontMetrics().getAscent());
	}
}
