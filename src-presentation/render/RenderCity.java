package render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import game.GameColor;
import game.city.City;
import game.player.PlayerRole;

public class RenderCity {
	private static final int CITY_RADIUS = 30;
	private int x;
	private int y;
	private City city;

	public RenderCity(int x, int y, City city) {
		this.x = x;
		this.y = y;
		this.city = city;
	}

	public void renderNeighborLine(Graphics2D g, RenderCity neighbor, int boardWidth) {
		if (neighbor.x < x)
			return;
		g.setColor(Color.LIGHT_GRAY);
		g.setStroke(new BasicStroke(3));
		if (neighbor.x - x > (x - neighbor.x + boardWidth)) {
			int nx = neighbor.x + CITY_RADIUS - boardWidth;
			int dy = neighbor.y - y;
			double r = (double) (x + CITY_RADIUS) / (Math.abs(nx));
			double b = dy * r / (1 + r);

			int middleY = (int) (y + b + CITY_RADIUS);
			g.drawLine(x + CITY_RADIUS, y + CITY_RADIUS, nx, middleY);
			g.drawLine(neighbor.x + CITY_RADIUS, neighbor.y + CITY_RADIUS, boardWidth + x, middleY);
		} else {
			g.drawLine(x + CITY_RADIUS, y + CITY_RADIUS, neighbor.x + CITY_RADIUS, neighbor.y + CITY_RADIUS);
		}

	}

	public void renderCityOnBoard(Graphics2D g, Render render) {
		g.setColor(render.getRenderColor(city.getColor()));
		g.fillOval(x, y, CITY_RADIUS * 2, CITY_RADIUS * 2);

		g.setFont(g.getFont().deriveFont(30f));

		if (city.hasResearchStation()) {
			g.setColor(new Color(228, 180, 34));
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawString(city.getName(), x, y + CITY_RADIUS * 2 + g.getFontMetrics().getAscent());
		drawCubes(g, render);
	}

	public void renderPlayer(Graphics2D g2d, int id, RenderPlayer renderPlayer, Render render) {
		final int PLAYER_PER_ROLE = 2;
		int xx = id % PLAYER_PER_ROLE;
		int yy = id / PLAYER_PER_ROLE;
		renderPlayer.renderPlayerAt(g2d, x + xx * CITY_RADIUS, y + yy * CITY_RADIUS, CITY_RADIUS, render);
	}

	private void drawCubes(Graphics2D g, Render render) {
		int i = 0;
		for (GameColor color : GameColor.values()) {
			for (int j = 0; j < city.getDiseaseCubeCount(color); j++) {

				drawDiseaseCube(g, i, render, color);
				i++;
			}
		}

	}

	public void drawDiseaseCube(Graphics2D g, int i, Render render, GameColor color) {
		final int OUT_SIZE = 22;
		final int INNER_SIZE = 16;
		final int CUBE_PER_LINE = 6;
		int xx = i % CUBE_PER_LINE;
		int yy = i / CUBE_PER_LINE;
		g.setColor(Color.WHITE);
		g.fillRect(xx * OUT_SIZE + x + CITY_RADIUS * 2, yy * OUT_SIZE + y, OUT_SIZE, OUT_SIZE);
		g.setColor(render.getRenderColor(color));
		int offset = (OUT_SIZE - INNER_SIZE) / 2;
		g.fillRect(xx * OUT_SIZE + offset + x + CITY_RADIUS * 2, yy * OUT_SIZE + offset + y, INNER_SIZE, INNER_SIZE);
	}
}
