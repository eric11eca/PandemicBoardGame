package render;

import java.awt.Color;
import java.awt.Graphics2D;

import game.cards.Card;
import game.city.City;
import game.event.Event;

public class RenderCard {
	private static final int WIDTH = 250;
	private static final int HEIGHT = 150;
	private static final int CIRCLE_SIZE = 25;
	private static final int BORDER = 10;

	public void setRenderSize(Graphics2D g, int width, int height) {
		float scaleX = (float) width / WIDTH;
		float scaleY = (float) height / HEIGHT;
		float scale = Math.min(scaleX, scaleY);
		g.scale(scale, scale);
	}

	public void renderCard(Graphics2D g, Card card, Render render) {
		card.getCity().ifPresent(city -> renderCityCard(g, city, render));
		card.getEvent().ifPresent(event -> renderEventCard(g, event));
	}

	public void renderBackground(Graphics2D g) {
		g.fillRect(0, 0, WIDTH, HEIGHT);
	}

	private void renderCityCard(Graphics2D g, City city, Render render) {
		g.setColor(render.getRenderColor(city.getColor()));
		g.fillRect(0, 0, WIDTH, BORDER);
		g.fillRect(0, BORDER, BORDER, HEIGHT - BORDER * 2);
		g.fillRect(WIDTH - BORDER, BORDER, BORDER, HEIGHT - BORDER * 2);
		g.fillRect(0, HEIGHT - BORDER, WIDTH, BORDER);
		g.fillOval((WIDTH - CIRCLE_SIZE) / 2, (HEIGHT - CIRCLE_SIZE) / 4, CIRCLE_SIZE, CIRCLE_SIZE);

		g.setFont(g.getFont().deriveFont((float) 30));
		int h = g.getFontMetrics().getHeight();
		int w = g.getFontMetrics().stringWidth(city.getName());
		g.drawString(city.getName(), (WIDTH - w) / 2, HEIGHT / 2 + g.getFontMetrics().getAscent());
	}

	private void renderEventCard(Graphics2D g, Event event) {
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, WIDTH, BORDER);
		g.fillRect(0, BORDER, BORDER, HEIGHT - BORDER * 2);
		g.fillRect(WIDTH - BORDER, BORDER, BORDER, HEIGHT - BORDER * 2);
		g.fillRect(0, HEIGHT - BORDER, WIDTH, BORDER);
		g.fillOval((WIDTH - CIRCLE_SIZE) / 2, (HEIGHT - CIRCLE_SIZE) / 4, CIRCLE_SIZE, CIRCLE_SIZE);

		g.setFont(g.getFont().deriveFont((float) 30));
		int h = g.getFontMetrics().getHeight();
		int w = g.getFontMetrics().stringWidth(event.getName());
		g.drawString(event.getName(), (WIDTH - w) / 2, HEIGHT / 2 + g.getFontMetrics().getAscent());
	}
}
